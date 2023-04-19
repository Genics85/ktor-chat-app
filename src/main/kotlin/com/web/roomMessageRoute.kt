package com.web

import com.components.message.controllers.RoomMessageControllerImpl
import com.components.message.models.RoomMessageDTO
import com.components.message.repo.RoomMessageDAOImpl
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
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
        get("/get-room-messages/{roomId}"){
            val messageId = call.parameters["roomId"] ?: throw BadRequestException("room id can not be null in url param")
            val response = roomMessageInst.getRoomMessage(messageId)
            call.respond(response)
        }
        get("/get-message/{id}"){
            val messageId = call.parameters["id"] ?: throw BadRequestException("Id for message can't be null in url param")
            val response = roomMessageInst.getRoomMessage(messageId)
            call.respond(response)
        }
        delete("/delete/{id}"){
            val messageId = call.parameters["id"] ?: throw BadRequestException("id for message to be deleted cant be null")
            val response = roomMessageInst.deleteRoomMessage(messageId)
            call.respond(response)
        }
        post("/create"){
            val message = call.receive<RoomMessageDTO>()
            val response = roomMessageInst.createRoomMessage(message)
            call.respond(response)
        }
        delete("/delete-all-messages/{id}"){
            val roomId = call.parameters["id"] ?: throw BadRequestException("id for room can't be null in url param")
            val response = roomMessageInst.deleteAllRoomMessages(roomId)
            call.respond(response)
        }
    }
}