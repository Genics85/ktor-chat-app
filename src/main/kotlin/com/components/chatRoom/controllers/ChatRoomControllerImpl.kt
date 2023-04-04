package com.components.chatRoom.controllers

import com.components.chatRoom.models.ChatRoom
import com.components.message.models.RoomMessage
import com.config.APIResponse

class ChatRoomControllerImpl : ChatRoomController {
    /**
     * function to create a chat room
     * **/
    override fun createChatRoom(chatRoom: ChatRoom): APIResponse<ChatRoom> {
        TODO("Not yet implemented")
    }

    /**
     * function to get a chat room
     * **/
    override fun getChatRoom(id: String): APIResponse<ChatRoom?> {
        TODO("Not yet implemented")
    }

    /**
     * function to delete a chat room
     * **/
    override fun deleteChatRoom(id: String): APIResponse<Boolean> {
        TODO("Not yet implemented")
    }

    /**
     * get chat room messages
     * **/
    override fun getChatRoomMessages(id: String): APIResponse<List<RoomMessage>> {
        TODO("Not yet implemented")
    }

    /**
     * function to get a specific chat room message
     * **/
    override fun getAChatRoomMessage(chatRoomId: String, messageId: String): APIResponse<RoomMessage> {
        TODO("Not yet implemented")
    }

    /**
     * function to change chat room name
     * **/
    override fun changeChatRoomName(chatRoomId: String, newName: String): APIResponse<String> {
        TODO("Not yet implemented")
    }

    /**
     * function to add a user/s to chat room
     * **/
    override fun addUserToChatRoom(chatRoomId: String, usersId: List<String>): APIResponse<Int> {
        TODO("Not yet implemented")
    }

    /**
     * function to delete user/s from chat room
     * **/
    override fun deleteUsersFromChatRoom(chatRoomId: String, usersId: List<String>): APIResponse<Int> {
        TODO("Not yet implemented")
    }

    /**
     * function to get members of a chat room
     * **/
    override fun getMembersOfChatRoom(chatRoomId: String): APIResponse<List<String>> {
        TODO("Not yet implemented")
    }
}