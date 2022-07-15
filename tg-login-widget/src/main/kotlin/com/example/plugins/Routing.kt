package com.example.plugins

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long


fun Application.configureRouting(bot: Bot, settings: BotSettings) {
    routing {
        get("/") {
            call.respond(ThymeleafContent("index", mapOf("bot" to settings.name)))
        }
        post("/users") {
            val user = call.receive<JsonObject>()
            // in production hash should be verified before going further
            bot.sendMessage(
                ChatId.fromId(user["id"]!!.jsonPrimitive.long),
                "You're successfully logged in, ${user["first_name"]!!.jsonPrimitive.content}!"
            )
            call.respond(HttpStatusCode.Created)
        }
        post("/webhook") {
            bot.processUpdate(call.receiveText())
            call.respond(HttpStatusCode.OK)
        }
    }
}
