package com.components.message.controllers

import com.components.message.models.DirectMessage
import com.config.APIResponse
import org.kodein.di.DI
import org.kodein.di.DIAware

class DirectMessageControllerImpl(override val di: DI) : DirectMessageControlller, DIAware {
    /**
     * function to get a single message with id
     * **/
    override fun getDirectMessage(messageId: String): APIResponse<DirectMessage?> {
        TODO("Not yet implemented")
    }

    /**
     * function to get all direct messages for a particular recipient
     *
     **/
    override fun getDirectMessagesForRecipient(recipientId: String): APIResponse<List<DirectMessage?>> {
        TODO("Not yet implemented")
    }

    /**
     * function to get all direct messages from a particular user
     * **/
    override fun getDirectMessagesFromSender(senderId: String): APIResponse<List<DirectMessage?>> {
        TODO("Not yet implemented")
    }

    /**
     * create direct message
     * **/
    override fun createDirectMessage(message: DirectMessage): APIResponse<DirectMessage> {
        TODO("Not yet implemented")
    }

    /**
     * Function to delete direct message
     **/
    override fun deleteDirectMessage(messageId: String): APIResponse<Boolean> {
        TODO("Not yet implemented")
    }

    /**
     * get all direct messages
     * **/
    override fun getAllDirectMessages(): APIResponse<List<DirectMessage?>> {
        TODO("Not yet implemented")
    }
}