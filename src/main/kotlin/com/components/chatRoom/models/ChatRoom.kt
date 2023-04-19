package com.components.chatRoom.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class ChatRoom(
    val id:String = UUID.randomUUID().toString(),
    var membersIDs:MutableList<String>,
    val chatInitiator:String,
    var name:String,
    val dateCreated:LocalDateTime?= LocalDateTime.now()
)

@Serializable
data class ChatRoomDTO(
    var membersIDs: MutableList<String>,
    val chatInitiator: String,
    var name: String
)

object ChatRoomMapper{
    fun toModel(roomDTO: ChatRoomDTO):ChatRoom{
        return ChatRoom(
            membersIDs = roomDTO.membersIDs,
            name = roomDTO.name,
            chatInitiator = roomDTO.chatInitiator
        )
    }
    fun toDTO(chatRoom:ChatRoom):ChatRoomDTO{
        return ChatRoomDTO(
            membersIDs = chatRoom.membersIDs,
            name = chatRoom.name,
            chatInitiator = chatRoom.chatInitiator
        )
    }
}
