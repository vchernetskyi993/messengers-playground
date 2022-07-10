package com.example

import it.tdlight.common.ExceptionHandler
import it.tdlight.common.Init
import it.tdlight.common.ResultHandler
import it.tdlight.common.TelegramClient
import it.tdlight.jni.TdApi
import it.tdlight.tdlight.ClientManager
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.publisher.MonoSink

val client: TelegramClient = ClientManager.create()

private val log: Logger = LoggerFactory.getLogger("com.example.Client")

suspend fun initializeClient() {
    Init.start()
    Mono.create { authorized ->
        client.initialize(onUpdate(authorized), onError, onError)
    }.awaitSingleOrNull()
}

private fun onUpdate(authorized: MonoSink<Unit>) = ResultHandler<TdApi.Update> { update ->
//    log.info("Received update: $update")
    when (update) {
        is TdApi.UpdateAuthorizationState -> onAuth(update.authorizationState, authorized)
//        else -> log.warn("Update type ${update.javaClass} not supported")
    }
}

private val onError = ExceptionHandler { e -> log.error(e.message, e) }
