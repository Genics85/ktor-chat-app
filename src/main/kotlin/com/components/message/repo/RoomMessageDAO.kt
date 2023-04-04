package com.components.message.repo

import com.components.message.models.RoomMessage

interface RoomMessageDAO {
    /***
     * function to create a room message
     * */
    fun createRoomMessage(message:RoomMessage):RoomMessage
    /**
     * function to delete room message
     * **/
    fun deleteRoomMessage(id:String):Boolean
    /**
     * function to get all messages for a particular room
     * **/
    fun getMessagesForRoom(roomId:String):List<RoomMessage?>
    /**
     * function to get all room messages
     * **/
    fun getRoomMessages():List<RoomMessage?>
}