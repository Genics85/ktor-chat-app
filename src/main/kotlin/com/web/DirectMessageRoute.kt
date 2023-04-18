package com.web

import com.components.message.controllers.DirectMessageControllerImpl
import com.components.message.models.DirectMessageDTO
import com.components.message.repo.DirectMessageDAOImpl
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val BASE_URL = "/api/direct-message"

fun Routing.directMessageRoute(){
    val di: DI=DI{
        bindSingleton{ DirectMessageDAOImpl()}
    }
    val directMessInst = DirectMessageControllerImpl(di)

    route(BASE_URL){
        get("/get-all"){
            val response = directMessInst.getAllDirectMessages()
            call.respond(response)
        }
        get("/get/{id}"){
            val messageId = call.parameters["id"] ?: throw BadRequestException("id parameter for message cant be null")
            val response = directMessInst.getDirectMessage(messageId)
            call.respond(response)
        }
        delete("/delete/{id}"){
            val messageId = call.parameters["id"] ?: throw BadRequestException("id parameter for message cant be null")
            val response = directMessInst.deleteDirectMessage(messageId)
            call.respond(response)
        }
        post("/create"){
            val directMessageDto = call.receive<DirectMessageDTO>()
            val response = directMessInst.createDirectMessage(directMessageDto)
            call.respond(response)
        }
        get("/get-sender-messages/{senderId}"){
            val senderId = call.parameters["senderId"] ?: throw BadRequestException("Sender Id in request param cant be null")
            val response = directMessInst.getDirectMessagesFromSender(senderId)
            call.respond(response)
        }
        get("/get-recipient-messages/{recipientId}"){
            val recipientId = call.parameters["recipientId"] ?: throw BadRequestException("Recipient id in param cant be null")
            val response = directMessInst.getDirectMessagesForRecipient(recipientId)
            call.respond(response)
        }
    }
}