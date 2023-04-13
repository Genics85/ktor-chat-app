package com.components.message.controllers

import com.components.message.models.RoomMessage
import com.config.APIResponse

interface RoomMessageController {
    /**
     * function to get all chat room messages
     * **/
    fun getRooMessages():APIResponse<List<RoomMessage>>
    /**
     * function to delete a chat room message
     * **/
    fun deleteRoomMessage(messageId:String):APIResponse<Boolean>
    /**
     * function to create room message
     * **/
    fun createRoomMessage(message:RoomMessage): APIResponse<Int>
    /**
     * function to get specific message in a chat room
     * **/
    fun getRoomMessage(messageId: String):APIResponse<RoomMessage>
    /**
     * function to delete all messages in chat room
     * **/
    fun deleteAllRoomMessages(roomId:String):APIResponse<Boolean>
}