package com.components.message.models

data class DirectMessage(
    val _id:String,
    val senderId:String,
    val recipientId:String,
    val content:Any,
    val timeSent:String,
)
