package com.components.message.repo

import com.components.message.models.DirectMessage

interface DirectMessageDAO {
    /**
     * function to get a single direct message
     * **/
    fun getDirectMessage(messageId:String):DirectMessage?
    /**
     * function to create a direct message
     * **/
    fun createDirectMessage(message:DirectMessage): Int
    /**
     * function to delete direct message
     * **/
    fun deleteDirectMessage(id:String):Boolean
    /**
     * function to get a direct message for a particular user
     * **/
    fun getDirectMessagesForRecipient(recipientId:String):List<DirectMessage>
    /**
     * function to get all direct messages from a sender
     * **/
    fun getDirectMessageFromSender(senderId:String):List<DirectMessage>
    /**
     * function to get all direct messages
     * **/
    fun getAllDirectMessages():List<DirectMessage>
}