package com.components.chatRoom.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ChatRoom(
    val id:String,
    var membersIDs:MutableList<String>,
    val chatInitiator:String,
    var name:String,
    val dateCreated:LocalDateTime
)
