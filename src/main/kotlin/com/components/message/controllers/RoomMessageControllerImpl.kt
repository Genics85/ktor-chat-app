package com.components.message.controllers

import com.components.message.models.RoomMessage
import com.components.message.repo.RoomMessageDAO
import com.config.APIResponse
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.sql.SQLException

class RoomMessageControllerImpl(override val di: DI) : RoomMessageController, DIAware {
    private val roomMessageDao: RoomMessageDAO by di.instance()
    /**
     * function to get all chat room messages
     * **/
    override fun getRooMessages(): APIResponse<List<RoomMessage>> {
        val roomMessages:APIResponse<List<RoomMessage>> = try{
            val roomMessages = roomMessageDao.getAllRoomMessages()
            if(roomMessages.isNotEmpty()){
                APIResponse("201","40","Retrieved all room messages with success",listOf(roomMessages))
            }else{
                APIResponse("204","40","Room has no messages or does not exist",listOf())
            }
        }catch(se: SQLException){
            APIResponse("500","40","Can't get room messages because of SE error",listOf())
        }
        return roomMessages
    }

    /**
     * function to delete a chat room message
     * **/
    override fun deleteRoomMessage(messageId: String): APIResponse<Boolean> {
        val deletedMessage:APIResponse<Boolean> = try{
            val isDeleted = roomMessageDao.deleteRoomMessage(messageId)
            if(isDeleted){
                APIResponse("200","40","Delete room message with success",listOf())
            }else{
                APIResponse("204","40","couldn't delete message because",listOf())
            }
        }catch(se:SQLException){
            APIResponse("500","40","Cant delete room message because of SE error",listOf())
        }
        return deletedMessage
    }

    /**
     * function to create room message
     * **/
    override fun createRoomMessage(message: RoomMessage): APIResponse<Int> {
        val createdMessage:APIResponse<Int> = try{
            val createdCount = roomMessageDao.createRoomMessage(message)
            if(createdCount > 0){
                APIResponse("200","40","Created room message with success",listOf())
            }else{
                APIResponse("204","40","Couldn't create room message",listOf())
            }
        }catch(se:SQLException){
            APIResponse("500","40","Couldn't create room message because of SE error",listOf())
        }
        return createdMessage
    }

    /**
     * function to get specific message in a chat room
     * **/
    override fun getRoomMessage(messageId: String): APIResponse<RoomMessage> {
        val gotRoom:APIResponse<RoomMessage> = try{
            val message = roomMessageDao.getRoomMessage(messageId)
            if(message != null){
                APIResponse("201","40","Got room message with success",listOf(message))
            }else{
                APIResponse("204","40","Failed to get room message or id not found",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","40","Couldn't get room message because of SE error",listOf())
        }
        return gotRoom
    }

    /**
     * function to delete all messages in chat room
     * **/
    override fun deleteAllRoomMessages(roomId:String): APIResponse<Boolean> {
        val deleteAll:APIResponse<Boolean> = try{
            val isDeleted= roomMessageDao.deleteAllRoomMessages(roomId)
            if(isDeleted){
                APIResponse("200","40","Deleted all room messages with success",listOf())
            }else{
                APIResponse("204","40","Failed to delete all room messages",listOf())
            }
        }catch(se:SQLException){
            APIResponse("500","40","Failed to delete all room messages because of SE error",listOf())
        }
        return deleteAll
    }
}