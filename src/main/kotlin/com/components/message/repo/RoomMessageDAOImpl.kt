package com.components.message.repo

import com.components.message.models.RoomMessage

class RoomMessageDAOImpl : RoomMessageDAO {
    /***
     * function to create a room message
     * */
    override fun createRoomMessage(message: RoomMessage): RoomMessage {
        TODO("Not yet implemented")
    }

    /**
     * function to delete room message
     * **/
    override fun deleteRoomMessage(roomId: String, id: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * function to get all messages for a particular room
     * **/
    override fun getRoomMessages(roomId: String): List<RoomMessage?> {
        TODO("Not yet implemented")
    }

    /**
     * function to get all room messages
     * **/
    override fun getAllRoomMessages(): List<RoomMessage?> {
        TODO("Not yet implemented")
    }
}