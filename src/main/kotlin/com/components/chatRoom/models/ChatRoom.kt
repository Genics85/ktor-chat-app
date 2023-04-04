package com.components.chatRoom.models

import java.time.LocalDateTime

data class ChatRoom(
    val id:String,
    var membersIDs:List<String>,
    val chatInitiator:String,
    var roomName:String,
    val dateCreated:LocalDateTime
)
