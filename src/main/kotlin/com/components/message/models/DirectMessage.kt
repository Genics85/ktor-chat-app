package com.components.message.models

data class DirectMessage(
    val _id:String,
    val senderId:String,
    val recipientId:String,
    val content:String,
    val timeSent:String,
)
