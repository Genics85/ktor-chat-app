package com.components.message.controllers

import com.components.message.models.DirectMessage
import com.config.APIResponse

interface DirectMessageController {
    /**
     * function to get a single message with id
     * **/
    fun getDirectMessage(messageId:String):APIResponse<DirectMessage?>
    /**
     * function to get all direct messages for a particular recipient
     *
    **/
    fun getDirectMessagesForRecipient(recipientId:String):APIResponse<List<DirectMessage?>>
    /**
     * function to get all direct messages from a particular user
     * **/
    fun getDirectMessagesFromSender(senderId:String):APIResponse<List<DirectMessage?>>
    /**
     * create direct message
     * **/
    fun createDirectMessage(message:DirectMessage):APIResponse<DirectMessage>
    /**
     * Function to delete direct message
     **/
    fun deleteDirectMessage(messageId:String):APIResponse<Boolean>
    /**
     * get all direct messages
     * **/
    fun getAllDirectMessages():APIResponse<List<DirectMessage?>>
}