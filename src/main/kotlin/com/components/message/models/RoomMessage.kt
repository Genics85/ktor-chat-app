package com.components.message.models

data class RoomMessage(
    val id:String,
    val senderId:String,
    val roomId:String,
    val content:Any,
    val timeSent:String,
)
