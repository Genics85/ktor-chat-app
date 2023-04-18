package com.web

import com.components.message.controllers.RoomMessageControllerImpl
import com.components.message.repo.RoomMessageDAOImpl
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val BASE_URL = "api/room-message"

val di: DI = DI{
    bindSingleton{RoomMessageDAOImpl()}
}
val roomMessageInst = RoomMessageControllerImpl(di)

fun Routing.roomMessageRoute(){
    route(BASE_URL){
        get("/get-room-message/{roomId}"){
            val messageId = call.parameters["roomId"] ?: throw BadRequestException("room id can not be null in url param")
            val response = roomMessageInst.getRoomMessage(messageId)
            call.respond(response)
        }
    }
}