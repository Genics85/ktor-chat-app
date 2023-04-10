package com.components.chatRoom.repo

import com.components.chatRoom.models.ChatRoom
import com.database.ChatRoomDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ChatRoomDAOImpl : ChatRoomDAO {

    /**
     * function to convert row from db to instance of chatroom model
     * **/
//    private fun rowToChatRoom(row: ResultRow) = ChatRoom(
//        id = row[ChatRoomDb.id],
//        chatInitiator = row[ChatRoomDb.chatInitiator],
//        name = row[ChatRoomDb.roomName],
//        dateCreated = row[ChatRoomDb.dateCreated]
//        membersIDs = row[ChatRoomDb.membersIDs]
//    )
    /**
     * function to get chat room details
     * **/
    override fun getChatRoom(id: String): ChatRoom? =transaction{
        TODO("WILL DO IT AFTER CHANGING MUTABLE LIST TO STRING")
//        ChatRoomDb.select(ChatRoomDb.id eq id)
//            .map(::rowToChatRoom)
//            .singleOrNull()
    }

    /**
     * function to create new chatroom
     * **/
    override fun createChatRoom(room: ChatRoom): Int = transaction{
        ChatRoomDb.insert {
            it[id]=room.id
            it[chatInitiator]=room.chatInitiator
            it[roomName]=room.name
            it[membersIDs]=room.membersIDs.toString()
            it[dateCreated]=room.dateCreated
        }.insertedCount
    }

    /**
     * function to get all chat room in db
     * **/
    override fun getAllChatRooms(): List<ChatRoom> = transaction {
        TODO("will do it after figuring out the chat room members list thing")
//        ChatRoomDb.selectAll()
//            .map(::rowToChatRoom)
//            .toList()
    }

    /**
     * function to delete chat room from database
     * **/
    override fun deleteChatRoom(id: String): Boolean =transaction{
        ChatRoomDb.deleteWhere { ChatRoomDb.id eq id } > 0
    }

    /**
     * Remove user from chat room
     * **/
    override fun deleteUserFromChatRoom(roomId: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Add user to the chat room
     * **/
    override fun addUserToChatRoom(roomId: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Function to change the name of a chat room
     * **/
    override fun changeChatRoomName(roomId: String, newName: String): Int = transaction {
        ChatRoomDb.update({ ChatRoomDb.id eq roomId }){
            it[roomName] = newName
        }
    }
}