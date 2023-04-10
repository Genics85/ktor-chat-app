package com.components.chatRoom.models

import java.time.LocalDateTime

data class ChatRoom(
    val id:String,
    var membersIDs:MutableList<String>,
    val chatInitiator:String,
    var name:String,
    val dateCreated:String
)
