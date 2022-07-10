package com.example

import it.tdlight.common.ResultHandler
import it.tdlight.jni.TdApi
import it.tdlight.jni.TdApi.SetTdlibParameters
import it.tdlight.jni.TdApi.TdlibParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.MonoSink

private val log: Logger = LoggerFactory.getLogger("com.example.Auth")

fun onAuth(state: TdApi.AuthorizationState, authorized: MonoSink<Unit>) {
    when (state) {
        is TdApi.AuthorizationStateWaitTdlibParameters -> {
            val parameters = TdlibParameters().apply {
                databaseDirectory = "tdlib"
                useMessageDatabase = true
                useSecretChats = true
                apiId = Settings.telegramApiId
                apiHash = Settings.telegramApiHash
                systemLanguageCode = "en"
                deviceModel = "Desktop"
                applicationVersion = "0.1.0"
                enableStorageOptimizer = true
            }

            client.send(
                SetTdlibParameters(parameters),
                onAuthResult("Tdlib parameters set")
            )
        }
        is TdApi.AuthorizationStateWaitEncryptionKey -> client.send(
            TdApi.CheckDatabaseEncryptionKey(),
            onAuthResult("Encryption key checked")
        )
        is TdApi.AuthorizationStateWaitPhoneNumber -> client.send(
            TdApi.SetAuthenticationPhoneNumber(
                Settings.senderPhone,
                null
            ), onAuthResult("Authentication phone sent")
        )
        is TdApi.AuthorizationStateWaitCode -> {
            print("Enter code: ")
            val code = readln()
            client.send(TdApi.CheckAuthenticationCode(code), onAuthResult("Code verified"))
        }
        is TdApi.AuthorizationStateWaitPassword -> {
            print("Enter password: ")
            val password = readln()
            client.send(
                TdApi.CheckAuthenticationPassword(password),
                onAuthResult("Password verified")
            )
        }
        is TdApi.AuthorizationStateReady -> authorized.success()
        else -> log.warn("Auth state $state not supported")
    }
}

private fun <R : TdApi.Object> onAuthResult(success: String) = ResultHandler<R> {
    when (it) {
        is TdApi.Error -> log.error("Auth error: $it")
        else -> log.info(success)
    }
}
