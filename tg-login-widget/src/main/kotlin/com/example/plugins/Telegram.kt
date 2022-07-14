package com.example.plugins

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.BotCommand
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.webhook

data class BotSettings(
    val token: String,
    val url: String,
)

fun configureBot(settings: BotSettings): Bot {
    val bot = bot {
        token = settings.token
        webhook {
            url = "${settings.url}/webhook"
        }
        dispatch {
            command("start") {
                bot.sendMessage(ChatId.fromId(message.chat.id), "Hello bot started.")
            }
            command("hello") {
                bot.sendMessage(ChatId.fromId(message.chat.id), "Hello World!")
            }
        }
    }
    bot.setMyCommands(
        listOf(
            BotCommand("start", "Initialize this bot."),
            BotCommand("hello", "Receive greeting from this bot.")
        )
    )
    bot.startWebhook()
    return bot
}
