package com.example

import it.tdlight.jni.TdApi
import kotlinx.coroutines.reactor.awaitSingle
import reactor.core.publisher.Mono

suspend fun fetchChatByUsername(user: String): Long =
    Mono.create { sink ->
        val request = TdApi.SearchPublicChat().apply {
            username = user
        }
        client.send(request) {
            when (it) {
                is TdApi.Error -> sink.error(Throwable("Error ${it.code}: ${it.message}"))
                is TdApi.Chat -> sink.success(it.id)
            }
        }
    }.awaitSingle()

suspend fun startBot(chat: Long, nonce: String): Unit =
    Mono.create { sink ->
        val request = TdApi.SendBotStartMessage().apply {
            botUserId = chat
            chatId = chat
            parameter = nonce
        }
        client.send(request) {
            when (it) {
                is TdApi.Error -> sink.error(Throwable("Error ${it.code}: ${it.message}"))
                is TdApi.ImportedContacts -> sink.success(Unit)
            }
        }
    }.awaitSingle()
