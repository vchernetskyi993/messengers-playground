package com.example

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    initializeClient()
    sendMessage(Settings.receiverPhone, "Hello World!")
}
