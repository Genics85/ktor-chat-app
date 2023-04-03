package com.components.chatRoom.repo

import com.components.chatRoom.models.ChatRoom

class ChatRoomDAOImpl : ChatRoomDAO {
    /**
     * function to get chat room details
     * **/
    override fun getChatRoom(id: String): ChatRoom {
        TODO("Not yet implemented")
    }

    /**
     * function to create new chatroom
     * **/
    override fun createChatRoom(room: ChatRoom): String {
        TODO("Not yet implemented")
    }

    /**
     * function to delete chat room from database
     * **/
    override fun deleteChatRoom(id: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Remove user from chat room
     * **/
    override fun deleteUserFromChatRoom(id: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Add user to the chat room
     * **/
    override fun addUserToChatRoom(id: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Function to change the name of a chat room
     * **/
    override fun changeChatRoomName(id: String, newName: String): Int {
        TODO("Not yet implemented")
    }
}