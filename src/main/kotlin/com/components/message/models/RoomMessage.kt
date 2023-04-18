package com.components.message.models

import java.time.LocalDateTime
import java.util.*

data class RoomMessage(
    val id:String = UUID.randomUUID().toString(),
    val senderId:String,
    val roomId:String,
    val content:String,
    val timeSent:LocalDateTime = LocalDateTime.now(),
)

data class RoomMessageDTO(
    val senderId: String,
    val roomId: String,
    val content: String
)

object RoomMessageMapper{
    fun toModel(roomMessageDto:RoomMessageDTO):RoomMessage{
        return RoomMessage(
            senderId = roomMessageDto.senderId,
            roomId = roomMessageDto.roomId,
            content = roomMessageDto.content,
        )
    }

    fun toDTO(roomMessage:RoomMessage):RoomMessageDTO{
        return RoomMessageDTO(
            senderId = roomMessage.senderId,
            roomId = roomMessage.roomId,
            content = roomMessage.content
        )
    }
}