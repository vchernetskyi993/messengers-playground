package com.example

import com.example.plugins.configureBot
import io.ktor.server.application.*
import com.example.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureThymeleaf()
    val settings = BotSettings(
        environment.config.property("telegram.bot").getString(),
        environment.config.property("telegram.token").getString(),
        environment.config.property("telegram.webhook.url").getString()
    )
    val bot = configureBot(settings)
    configureRouting(bot, settings)
    configureSerialization()
}
