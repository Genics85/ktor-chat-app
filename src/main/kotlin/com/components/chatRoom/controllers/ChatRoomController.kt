package com.components.chatRoom.controllers

import com.components.chatRoom.models.ChatRoom
import com.components.message.models.RoomMessage
import com.config.APIResponse

interface ChatRoomController {
    /**
     * function to create a chat room
     * **/
    fun createChatRoom(chatRoom:ChatRoom):APIResponse<ChatRoom>
    /**
     * function to get a chat room
     * **/
    fun getChatRoom(id:String):APIResponse<ChatRoom?>
    /**
     * function to delete a chat room
     * **/
    fun deleteChatRoom(id:String):APIResponse<Boolean>
    /**
     * get chat room messages
     * **/
    fun getChatRoomMessages(id:String):APIResponse<List<RoomMessage>>
    /**
     * function to get a specific chat room message
     * **/
    fun getAChatRoomMessage(chatRoomId:String,messageId:String):APIResponse<RoomMessage>
    /**
     * function to change chat room name
     * **/
    fun changeChatRoomName(chatRoomId:String,newName:String):APIResponse<String>
    /**
     * function to add a user/s to chat room
     * **/
    fun addUserToChatRoom(chatRoomId:String,usersId:List<String>):APIResponse<Int>
    /**
     * function to delete user/s from chat room
     * **/
    fun deleteUsersFromChatRoom(chatRoomId:String,usersId: List<String>):APIResponse<Int>
    /**
     * function to get members of a chat room
     * **/
    fun getMembersOfChatRoom(chatRoomId:String):APIResponse<List<String>>
}