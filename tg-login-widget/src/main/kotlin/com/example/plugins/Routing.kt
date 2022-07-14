package com.example.plugins

import com.github.kotlintelegrambot.Bot
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting(bot: Bot) {
    routing {
        post("/webhook") {
            bot.processUpdate(call.receiveText())
            call.respond(HttpStatusCode.OK)
        }
    }
}
