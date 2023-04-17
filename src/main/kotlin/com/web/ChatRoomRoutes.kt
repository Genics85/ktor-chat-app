package com.web

import com.components.chatRoom.controllers.ChatRoomControllerImpl
import com.components.chatRoom.models.ChatRoomDTO
import com.components.chatRoom.repo.ChatRoomDAOImpl
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private val BASE_URL = "/api/room"

fun Routing.chatRoomRoutes(){
    val di:DI= DI{
        bindSingleton{ChatRoomDAOImpl()}
    }
    val roomInst = ChatRoomControllerImpl(di)

    route(BASE_URL){
        get("/get/id"){
            val roomId = call.parameters["id"] ?: throw BadRequestException ("Parameter can't be null to ge a room")
            val response = roomInst.getChatRoom(roomId)
            call.respond(response)
        }
        get("/get-members/{id}"){
            val chatRoomId = call.parameters["id"] ?: throw BadRequestException("Parameter id for chat room can't be null")
            val response = roomInst.getMembersOfChatRoom(chatRoomId)
            call.respond(response)
        }
        get("/get-messages/{id}"){
            val roomId = call.parameters["id"] ?: throw BadRequestException("Parameter id for room cant be null")
            val response = roomInst.getChatRoomMessages(roomId)
            call.respond(response)
        }
        get("/get-message/{id}"){
            val messageId = call.parameters["id"] ?: throw BadRequestException("Parameter id for messaged can not be null")
            val response = roomInst.getAChatRoomMessage(messageId)
            call.respond(response)
        }
        post("/add"){
            val room = call.receive<ChatRoomDTO>()
            val response = roomInst.createChatRoom(room)
        }
    }
}