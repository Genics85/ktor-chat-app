package com.components.message.controllers

import com.components.message.models.DirectMessage
import com.components.message.models.DirectMessageDTO
import com.components.message.repo.DirectMessageDAO
import com.config.APIResponse
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.sql.SQLException

class DirectMessageControllerImpl(override val di: DI) : DirectMessageController, DIAware {

   private val messageDAO: DirectMessageDAO by di.instance()
    /**
     * function to get a single message with id
     * **/
    override fun getDirectMessage(messageId: String): APIResponse<DirectMessage> {

        val directMessage:APIResponse<DirectMessage> = try{

            if(messageId != null){
                val message = messageDAO.getDirectMessage(messageId)
                if(message != null){
                    APIResponse("201","30","got direct message with success",listOf(message))
                }else{
                    APIResponse("204","30","No direct message is found with this id",listOf())
                }
            }else{
                APIResponse("404","30","Couldn't get direct message because id is null", listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","30","Couldn't get direct message because of $se error",listOf())
        }
        return  directMessage
    }

    /**
     * function to get all direct messages for a particular recipient
     *
     **/
    override fun getDirectMessagesForRecipient(recipientId: String): APIResponse<List<DirectMessage>> {
        val getDirMessages:APIResponse<List<DirectMessage>> =try{
            if(recipientId != null){
                val messages= messageDAO.getDirectMessagesForRecipient(recipientId)
                if(messages.isNotEmpty()){
                    APIResponse("201","30","got all messages for a recipient with success", listOf(messages))
                }else{
                    APIResponse("204","30","there is no messages for recipient with the id",listOf())
                }
            }else{
                APIResponse("404","30","couldn't get direct messages for recipient because id is null",listOf())
            }
        }catch(se:SQLException){
            APIResponse("500","30","couldn't get direct messages from user because of $se error",listOf())
        }
        return getDirMessages
    }

    /**
     * function to get all direct messages from a particular user
     * **/
    override fun getDirectMessagesFromSender(senderId: String): APIResponse<List<DirectMessage>> {
        val getDirMessages:APIResponse<List<DirectMessage>> =try{
            if(senderId != null){
                val messages= messageDAO.getDirectMessageFromSender(senderId)
                if(messages.isNotEmpty()){
                    APIResponse("201","30","got all messages for a recipient with success", listOf(messages))
                }else{
                    APIResponse("204","30","there is no messages for recipient with the id",listOf())
                }
            }else{
                APIResponse("404","30","couldn't get direct messages for recipient because id is null",listOf())
            }
        }catch(se:SQLException){
            APIResponse("500","30","couldn't get direct messages from user because of $se error",listOf())
        }
        return getDirMessages
    }

    /**
     * create direct message
     * **/
    override fun createDirectMessage(message: DirectMessageDTO): APIResponse<Int> {
        val createdMessage:APIResponse<Int> = try{
            if(message != null){
                val isCreated= messageDAO.createDirectMessage(message)
                if(isCreated > 0){
                    APIResponse("200","30","direct message created with success",listOf())
                }else{
                    APIResponse("204","30","failed to create direct message",listOf())
                }
            }else{
                APIResponse("404","30","couldn't create message because its null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","30","Couldn't create direct messages because of $se error",listOf())
        }
        return createdMessage
    }

    /**
     * Function to delete direct message
     **/
    override fun deleteDirectMessage(messageId: String): APIResponse<Boolean> {
        val deletedMessage:APIResponse<Boolean> = try{
            if(messageId != null){
                val isDeleted = messageDAO.deleteDirectMessage(messageId)
                if(isDeleted){
                    APIResponse("200","30","deleted direct message with success",listOf())
                }else{
                    APIResponse("204","30","Couldn't delete direct message because id does not exist",listOf())
                }
            }else{
                APIResponse("404","30","couldn't delete message because id is null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","30","couldn't delete message because of $se error",listOf())
        }
        return deletedMessage
    }

    /**
     * get all direct messages
     * **/
    override fun getAllDirectMessages(): APIResponse<List<DirectMessage>> {
        val allMessages:APIResponse<List<DirectMessage>> = try{
            val allDirectMessages = messageDAO.getAllDirectMessages()
            if(allDirectMessages.isNotEmpty()){
                APIResponse("201","30","got all direct messages with success", listOf(allDirectMessages))
            }else{
                APIResponse("204","30","direct message database is empty",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","30","Couldn't get all direct messages because of $se error", listOf())
        }
        return allMessages
    }

}