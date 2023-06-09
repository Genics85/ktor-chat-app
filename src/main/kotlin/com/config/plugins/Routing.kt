package com.config.plugins

import com.generis.web.userRoutes
import com.web.chatRoomRoutes
import com.web.directMessageRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        userRoutes()
        chatRoomRoutes()
        directMessageRoute()

    }
}
