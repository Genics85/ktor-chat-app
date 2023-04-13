package com.components.message.models

import java.time.LocalDateTime

data class DirectMessage(
    val id:String,
    val senderId:String,
    val recipientId:String,
    val content:String,
    val timeSent:LocalDateTime,
)
