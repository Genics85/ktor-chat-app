package com.components.message.models

data class DirectMessage(
    val id:String,
    val senderId:String,
    val recipientId:String,
    val content:Any,
    val timeSent:String,
)
