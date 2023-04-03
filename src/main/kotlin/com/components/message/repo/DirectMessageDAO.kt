package com.components.message.repo

import com.components.message.models.DirectMessage

interface DirectMessageDAO {
    /**
     * function to create a direct message
     * **/
    fun createDirectMessage(message:DirectMessage):DirectMessage
    /**
     * function to delete direct message
     * **/
    fun deleteDirectMessage(id:String):Boolean
}