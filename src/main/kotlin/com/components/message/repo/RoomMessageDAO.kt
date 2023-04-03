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
}