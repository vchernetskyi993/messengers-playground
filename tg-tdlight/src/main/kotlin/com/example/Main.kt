package com.example

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


fun main() = runBlocking {
    initializeClient()
    val chatId = fetchChatByUsername(Settings.bot)
    startBot(chatId, newNonce())
}

suspend fun newNonce(): String =
    HttpClient().use { client ->
        val responseBody = client.post {
            url {
                host = "localhost"
                port = 8080
                path("/api/telegram/connect")
            }
        }.body<String>()

        Json.parseToJsonElement(responseBody)
            .jsonObject["connection"]?.jsonObject
            ?.get("nonce")?.jsonPrimitive?.content
            ?: throw IllegalStateException("Invalid nonce response")
    }
