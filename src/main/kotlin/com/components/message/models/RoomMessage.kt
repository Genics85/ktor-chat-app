package com.components.message.models

data class RoomMessage(
    val _id:String,
    val senderId:String,
    val roomId:String,
    val content:String,
    val timeSent:String,
)
