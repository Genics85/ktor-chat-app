package com.components.message.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class DirectMessage(
    val id:String = UUID.randomUUID().toString(),
    val senderId:String,
    val recipientId:String,
    val content:String,
    val timeSent:LocalDateTime? = LocalDateTime.now(),
)

@Serializable
data class DirectMessageDTO(
    val senderId: String,
    val recipientId: String,
    val content: String,
)

object DirectMessageMapper{
    fun toModel(directMessageDTO:DirectMessageDTO):DirectMessage{
        return DirectMessage(
            senderId = directMessageDTO.senderId,
            recipientId = directMessageDTO.recipientId,
            content = directMessageDTO.content
        )
    }

    fun toDTO(directMessage: DirectMessage):DirectMessageDTO{
        return DirectMessageDTO(
            senderId = directMessage.senderId,
            recipientId = directMessage.recipientId,
            content = directMessage.content
        )
    }
}