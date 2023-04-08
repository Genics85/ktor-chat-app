package com.components.message.controllers

import com.components.message.models.RoomMessage
import com.config.APIResponse
import org.kodein.di.DI
import org.kodein.di.DIAware

class RoomMessageControllerImpl(override val di: DI) : RoomMessageController, DIAware {
    /**
     * function to get all chat room messages
     * **/
    override fun getRooMessages(roomId: String): APIResponse<List<RoomMessage>> {
        TODO("Not yet implemented")
    }

    /**
     * function to delete a chat room message
     * **/
    override fun deleteRoomMessage(messageId: String): APIResponse<Boolean> {
        TODO("Not yet implemented")
    }

    /**
     * function to create room message
     * **/
    override fun createRoomMessage(message: RoomMessage): APIResponse<RoomMessage> {
        TODO("Not yet implemented")
    }

    /**
     * function to get specific message in a chat room
     * **/
    override fun getRoomMessage(messageId: String): APIResponse<RoomMessage> {
        TODO("Not yet implemented")
    }

    /**
     * function to delete all messages in chat room
     * **/
    override fun deleteAllRoomMessages(roomId: String): APIResponse<Boolean> {
        TODO("Not yet implemented")
    }
}