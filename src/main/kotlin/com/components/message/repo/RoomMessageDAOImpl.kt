package com.components.message.repo

import com.components.message.models.RoomMessage
import com.database.RoomMessageDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class RoomMessageDAOImpl : RoomMessageDAO {
    /**
     * function to convert row from db to room message
     * **/
    private fun rowToRoomMessage(row: ResultRow) = RoomMessage(
        id = row[RoomMessageDb.id],
        senderId = row[RoomMessageDb.senderId],
        roomId = row[RoomMessageDb.roomId],
        content = row[RoomMessageDb.content],
        timeSent = row[RoomMessageDb.timeSent]
    )

    /***
     * function to create a room message
     * */
    override fun createRoomMessage(message: RoomMessage): Int = transaction {
        RoomMessageDb.insert {
            it[id] = message.id
            it[senderId] = message.senderId
            it[roomId] = message.roomId
            it[content] = message.content
            it[timeSent] = message.timeSent
        }.insertedCount
    }

    /**
     * function to delete room message
     * **/
    override fun deleteRoomMessage(messageId: String): Boolean = transaction{
        RoomMessageDb.deleteWhere { RoomMessageDb.id eq messageId } > 0
    }

    /**
     * function to get all messages for a particular room
     * **/
    override fun getRoomMessages(roomId: String): List<RoomMessage> = transaction{
        RoomMessageDb.select(RoomMessageDb.roomId eq roomId)
            .map(::rowToRoomMessage)
            .toList()
    }

    /**
     * function to get all room messages
     * **/
    override fun getRoomMessage(messageId: String): RoomMessage? = transaction {
        RoomMessageDb.select( RoomMessageDb.id eq messageId)
            .map(::rowToRoomMessage)
            .singleOrNull()
    }

    /**
     * function to get all room messages
     * **/
    override fun getAllRoomMessages(): List<RoomMessage> = transaction{
        RoomMessageDb.selectAll()
            .map(::rowToRoomMessage)
            .toList()
    }

    /**function to delete all room messages**/
    override fun deleteAllRoomMessages(roomId: String): Boolean = transaction{
        RoomMessageDb.deleteWhere { RoomMessageDb.roomId eq roomId } > 0
    }
}