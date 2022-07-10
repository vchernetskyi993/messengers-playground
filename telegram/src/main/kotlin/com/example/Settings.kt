package com.example

import com.typesafe.config.ConfigFactory

object Settings {
    private val conf = ConfigFactory.load()
    val telegramApiId = conf.getInt("telegram.api.id")
    val telegramApiHash: String = conf.getString("telegram.api.hash")
    val senderPhone: String = conf.getString("app.senderPhone")
    val receiverPhone: String = conf.getString("app.receiverPhone")
}
