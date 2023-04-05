package com.components.chatRoom.repo

import com.components.chatRoom.models.ChatRoom

interface ChatRoomDAO {
    /**
     * function to get chat room details
     * **/
    fun getChatRoom(roomId:String):ChatRoom?
    /**
     * function to create new chatroom
     * **/
    fun createChatRoom(room: ChatRoom):ChatRoom?
    /**
     * function to delete chat room from database
     * **/
    fun deleteChatRoom(roomId:String):Boolean
    /**
     * Remove user from chat room
     * **/
    fun deleteUserFromChatRoom(roomId:String,userId:String):Boolean
    /**
     * Add user to the chat room
     * **/
    fun addUserToChatRoom(roomId:String,userId:String):Boolean
    /**
     * Function to change the name of a chat room
     * **/
    fun changeChatRoomName(roomId:String,newName:String):Boolean

}