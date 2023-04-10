package com.components.message.repo

import com.components.message.models.RoomMessage

interface RoomMessageDAO {
    /***
     * function to create a room message
     * */
    fun createRoomMessage(message:RoomMessage): Int
    /**
     * function to delete room message
     * **/
    fun deleteRoomMessage(messageId:String):Boolean
    /**
     * function to get all messages for a particular room
     * **/
    fun getRoomMessages(roomId:String):List<RoomMessage>
    /**
     * function to get all room messages
     * **/
    fun getRoomMessage(messageId:String):RoomMessage?
    /**
     * function to get all room messages**/
    fun getAllRoomMessages():List<RoomMessage>
    /**function to delete all room messages**/
    fun deleteAllRoomMessages(roomId:String):Boolean
}