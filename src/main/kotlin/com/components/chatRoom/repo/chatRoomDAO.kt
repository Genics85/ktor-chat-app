package com.components.chatRoom.repo

import com.components.chatRoom.models.ChatRoom

interface ChatRoomDAO {
    /**
     * function to get chat room details
     * **/
    fun getChatRoom(id:String):ChatRoom
    /**
     * function to create new chatroom
     * **/
    fun createChatRoom(room: ChatRoom):String
    /**
     * function to delete chat room from database
     * **/
    fun deleteChatRoom(id:String):Boolean
    /**
     * Remove user from chat room
     * **/
    fun deleteUserFromChatRoom(id:String):Boolean
    /**
     * Add user to the chat room
     * **/
    fun addUserToChatRoom(id:String):Boolean
    /**
     * Function to change the name of a chat room
     * **/
    fun changeChatRoomName(id:String,newName:String):Int

}