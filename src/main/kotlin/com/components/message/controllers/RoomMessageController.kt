package com.components.message.controllers

import com.components.message.models.RoomMessage
import com.components.message.models.RoomMessageDTO
import com.config.APIResponse

interface RoomMessageController {
    /**
     * function to get all chat room messages
     * **/
    fun getRooMessages(roomId:String):APIResponse<List<RoomMessage>>
    /**
     * function to delete a chat room message
     * **/
    fun deleteRoomMessage(messageId:String):APIResponse<Boolean>
    /**
     * function to create room message
     * **/
    fun createRoomMessage(message: RoomMessageDTO): APIResponse<Int>
    /**
     * function to get specific message in a chat room
     * **/
    fun getRoomMessage(messageId: String):APIResponse<RoomMessage>
    /**
     * function to delete all messages in chat room
     * **/
    fun deleteAllRoomMessages(roomId:String):APIResponse<Boolean>
}