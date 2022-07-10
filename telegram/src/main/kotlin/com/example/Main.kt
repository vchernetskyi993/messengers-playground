package com.example

import it.tdlight.common.Init
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    Init.start()
    initializeClient()
    sendMessage(Settings.receiverPhone, "Hello World!")
}
