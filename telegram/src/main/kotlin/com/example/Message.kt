package com.example

import it.tdlight.jni.TdApi
import kotlinx.coroutines.reactor.awaitSingle
import reactor.core.publisher.Mono

suspend fun sendMessage(phone: String, message: String) {
    val userId = fetchUserId(phone)
    val chatId = fetchChatId(userId)

    sendMessage(chatId, message)
}

private suspend fun fetchUserId(phone: String): Long =
    Mono.create { sink ->
        val contact = TdApi.Contact().apply {
            phoneNumber = phone
        }
        client.send(TdApi.ImportContacts(arrayOf(contact))) {
            when (it) {
                is TdApi.Error -> sink.error(Throwable("Error ${it.code}: ${it.message}"))
                is TdApi.ImportedContacts -> sink.success(it.userIds[0])
            }
        }
    }.awaitSingle()

private suspend fun fetchChatId(userId: Long): Long =
    Mono.create { sink ->
        client.send(TdApi.CreatePrivateChat(userId, false)) {
            when (it) {
                is TdApi.Error -> sink.error(Throwable("Error ${it.code}: ${it.message}"))
                is TdApi.Chat -> sink.success(it.id)
            }
        }
    }.awaitSingle()

private suspend fun sendMessage(chat: Long, message: String) =
    Mono.create { sink ->
        client.send(TdApi.SendMessage().apply {
            chatId = chat
            inputMessageContent = TdApi.InputMessageText().apply {
                text = TdApi.FormattedText().apply { text = message }
            }
        }) {
            when (it) {
                is TdApi.Error -> sink.error(Throwable("Error ${it.code}: ${it.message}"))
                else -> sink.success(Unit)
            }
        }
    }.awaitSingle()
