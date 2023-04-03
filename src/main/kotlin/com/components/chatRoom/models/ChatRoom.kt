package com.components.chatRoom.models

import java.time.LocalDateTime

data class ChatRoom(
    val _id:String,
    var userIds:List<String>,
    val chatInitiator:String,
    var roomName:String,
    val dateCreated:LocalDateTime
)
