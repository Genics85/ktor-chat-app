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
    fun createChatRoom(room: ChatRoom): Int
    /**
     * function to get all chat room in db
     * **/
    fun getAllChatRooms():List<ChatRoom>
    /**
     * function to delete chat room from database
     * **/
    fun deleteChatRoom(roomId:String):Boolean
    /**
     * Remove user from chat room
     * **/
    fun deleteUserFromChatRoom(roomId:String,userId:List<String>):Boolean
    /**
     * Add user to the chat room
     * **/
    fun addUserToChatRoom(roomId:String,userId:List<String>):Boolean
    /**
     * function to update the users in a group remove/add
     * **/
    fun updateUsers(roomId:String,members:String):Boolean
    /**
     * Function to change the name of a chat room
     * **/
    fun changeChatRoomName(roomId:String,newName:String): Int

}