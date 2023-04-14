package com.components.chatRoom.controllers

import com.components.chatRoom.models.ChatRoom
import com.components.chatRoom.repo.ChatRoomDAO
import com.components.message.models.RoomMessage
import com.components.message.repo.RoomMessageDAO
import com.config.APIResponse
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.sql.SQLException

class ChatRoomControllerImpl(override val di: DI) : ChatRoomController,DIAware {
    private val roomDAO:ChatRoomDAO by di.instance()
    private val roomMessageDAO: RoomMessageDAO by di.instance()
    /**
     * function to create a chat room
     * **/
    override fun createChatRoom(chatRoom: ChatRoom): APIResponse<ChatRoom> {
        var createdRoom:APIResponse<ChatRoom> = try{
            if(chatRoom !=null){
                val result = roomDAO.createChatRoom(chatRoom)
                if(result>0){
                    APIResponse("200","20","Chat room created with success",listOf())
                }else{
                    APIResponse("204","20","Chat room was not able to be created",listOf())
                }
            }else{
                APIResponse("404","20","Couldn't create chat chatRoom because its null", listOf())
            }
        }catch(se:SQLException){
            APIResponse("500","20","Couldn't create chat room because of $se error",listOf())
        }
        return createdRoom
    }

    /**
     * function to get a chat room
     * **/
    override fun getChatRoom(id: String): APIResponse<ChatRoom?> {
        val getRoom:APIResponse<ChatRoom?> = try{
            if(id != null){
                val room = roomDAO.getChatRoom(id)
                if(room != null){
                    APIResponse("201","20","Chat room got with success",listOf(room))
                }else{
                    APIResponse("204","20","Couldn't get room of id $id, or doesn't exist",listOf())
                }
            }else{
                APIResponse("404","20","Cant get chat room of empty id",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","20","Cant get chat room because of $se error",listOf())
        }
        return getRoom
    }

    /**
     * function to delete a chat room
     * **/
    override fun deleteChatRoom(id: String): APIResponse<Boolean> {
        val roomDeleted:APIResponse<Boolean> = try{
            if(id != null){
                val isDeleted = roomDAO.deleteChatRoom(id)
                if(isDeleted){
                    APIResponse("200","20","chat room deleted with success",listOf())
                }else{
                    APIResponse(("204"),"20","couldn't delete chat room because id do not exist",listOf())
                }
            }else{
                APIResponse("404","20","Couldn't delete chat room because id is null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","20","Couldn't delete chat room becaus of $se exception",listOf())
        }
        return roomDeleted
    }

    /**
     * get chat room messages
     * **/
    override fun getChatRoomMessages(id: String): APIResponse<List<RoomMessage>> {
        val roomMessage:APIResponse<List<RoomMessage>> = try{
            if(id != null){
                val messages = roomMessageDAO.getRoomMessages(id)
                if(messages.isNotEmpty()){
                    APIResponse("201","20","got all chatroom messages", listOf(messages))
                }else{
                    APIResponse("204","20","Chat room has no message", listOf())
                }
            }else{
                APIResponse("404","20","Couldn't get chat room messages because id is null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","20","couldn't get chat room messages because of $se error",listOf())
        }
        return roomMessage
    }

    /**
     * function to get a specific chat room message
     * **/
    override fun getAChatRoomMessage(messageId: String): APIResponse<RoomMessage> {
        val roomMessage:APIResponse<RoomMessage> = try{
            if(messageId !=null){
                val message = roomMessageDAO.getRoomMessage(messageId)
                if(message != null){
                    APIResponse("201","20","Got chat room message with success",listOf(message))
                }else{
                    APIResponse("204","20","Couldn't get room message because id does not exist",listOf())
                }
            }else{
                APIResponse("404","20","Couldn't get chat room because id is null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","20","Couldn't get chatroom message because of $se error",listOf())
        }
        return roomMessage
    }

    /**
     * function to change chat room name
     * **/
    override fun changeChatRoomName(chatRoomId: String, newName: String): APIResponse<String> {
         val roomNameChanged:APIResponse<String> = try{
             if(chatRoomId != null){
                 val isChanged = roomDAO.changeChatRoomName(chatRoomId,newName)
                 if(isChanged > 0) {
                     APIResponse("200","20","chat room name changed successfully",listOf())
                 }else{
                     APIResponse("204","20","Chat room couldn't be deleted because it does not exist",listOf())
                 }
             }else{
                 APIResponse("404","20","Couldn't change room name because room id is null",listOf())
             }
        }catch(se:SQLException){
            APIResponse("500","20","Couldn't change room name because of $se error",listOf())
        }
        return roomNameChanged
    }

    /**
     * function to add a user/s to chat room
     * **/
    override fun addUserToChatRoom(chatRoomId: String, usersId: List<String>): APIResponse<Int> {
        val addedUserChatRoom:APIResponse<Int> = try{
            var isAdded = roomDAO.addUserToChatRoom(chatRoomId,usersId)
            if(isAdded){
                APIResponse("200","20","Added user to the chat room with success",listOf())
            }else{
                APIResponse("204","20","Failed to add users to chat room",listOf())
            }

        }catch (se:SQLException){
            APIResponse("500","20","Couldn't add user to chat room because of SE error", listOf())
        }
        return addedUserChatRoom
    }

    /**
     * function to delete user/s from chat room
     * **/
    override fun deleteUsersFromChatRoom(chatRoomId: String, usersId: List<String>): APIResponse<Int> {
        val deletedUserChatRoom:APIResponse<Int> = try{
            var isDeleted = roomDAO.deleteUserFromChatRoom(chatRoomId,usersId)
            if(isDeleted){
                APIResponse("200","20","Deleted users from the chat room with success",listOf())
            }else{
                APIResponse("204","20","Failed to delete users to chat room",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","20","Couldn't delete users from chat room because of SE error", listOf())
        }
        return deletedUserChatRoom
    }

    /**
     * function to get members of a chat room
     * **/
    override fun getMembersOfChatRoom(chatRoomId: String): APIResponse<List<String>> {
        val members:APIResponse<List<String>> = try{
            if(chatRoomId != null){
                val room = roomDAO.getChatRoom(chatRoomId)
                val roomMembers = room?.membersIDs ?: listOf()
                if(roomMembers.isNotEmpty()){
                    APIResponse("201","20","Got list of members in chat room",listOf(roomMembers))
                }else{
                    APIResponse("204","20","Room has no members", listOf())
                }
            }else{
                APIResponse("404","20","Couldn't get room because id was null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","20","Couldn't get room members because of $se error",listOf())
        }
        return members
    }

}