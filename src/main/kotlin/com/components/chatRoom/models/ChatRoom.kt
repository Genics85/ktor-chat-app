package com.components.chatRoom.models

import java.time.LocalDateTime

data class ChatRoom(
    val id:String,
    val userIds:List<String>,
    val chatInitiator:String,
    val roomName:String,
    val dateCreated:LocalDateTime
)
