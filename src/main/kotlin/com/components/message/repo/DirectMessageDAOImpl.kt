package com.components.message.repo

import com.components.message.models.DirectMessage

class DirectMessageDAOImpl : DirectMessageDAO {
    /**
     * function to create a direct message
     * **/
    override fun createDirectMessage(message: DirectMessage): DirectMessage {
        TODO("Not yet implemented")
    }

    /**
     * function to delete direct message
     * **/
    override fun deleteDirectMessage(id: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * function to get a direct message for a particular user
     * **/
    override fun getDirectMessagesForRecipient(recipientId: String): List<DirectMessage> {
        TODO("Not yet implemented")
    }

    /**
     * function to get all direct messages from a sender
     * **/
    override fun getDirectMessageFromSender(senderId: String): List<DirectMessage> {
        TODO("Not yet implemented")
    }

    /**
     * function to get all direct messages
     * **/
    override fun getAllDirectMessages(): List<DirectMessage> {
        TODO("Not yet implemented")
    }
}