package com

import com.config.DatabaseFactory
import com.config.configureDI
import com.config.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDI()
    DatabaseFactory.connect()
    configureRouting()
}
