package com.components.message.models

import java.time.LocalDateTime

data class RoomMessage(
    val id:String,
    val senderId:String,
    val roomId:String,
    val content:String,
    val timeSent:LocalDateTime,
)
