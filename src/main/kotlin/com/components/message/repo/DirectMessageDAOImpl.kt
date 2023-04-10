package com.components.message.repo

import com.components.message.models.DirectMessage
import com.database.DirectMessageDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DirectMessageDAOImpl : DirectMessageDAO {
    /**
     * fun to convert row to direct message
     * **/
    private fun rowToDirectMessage(row:ResultRow) = DirectMessage(
        id = row[DirectMessageDb.id],
        senderId = row[DirectMessageDb.senderId],
        recipientId = row[DirectMessageDb.recipientId],
        content = row[DirectMessageDb.content],
        timeSent = row[DirectMessageDb.timeSent]
    )
    /**
     * function to create a direct message
     * **/
    override fun createDirectMessage(message: DirectMessage): Int = transaction{
        DirectMessageDb.insert{
            it[id] = message.id
            it[senderId] = message.senderId
            it[recipientId] = message.recipientId
            it[content] = message.content
            it[timeSent] = message.timeSent
        }.insertedCount
    }

    /**
     * function to delete direct message
     * **/
    override fun deleteDirectMessage(id: String): Boolean =transaction{
        DirectMessageDb.deleteWhere { DirectMessageDb.id eq id } > 0
    }

    /**
     * function to get a direct message for a particular user
     * **/
    override fun getDirectMessagesForRecipient(recipientId: String): List<DirectMessage> = transaction{
        DirectMessageDb.select(DirectMessageDb.recipientId eq recipientId)
            .map(::rowToDirectMessage)
            .toList()
    }

    /**
     * function to get all direct messages from a sender
     * **/
    override fun getDirectMessageFromSender(senderId: String): List<DirectMessage> = transaction {
        DirectMessageDb.select(DirectMessageDb.senderId eq senderId)
            .map(::rowToDirectMessage)
            .toList()
    }

    /**
     * function to get all direct messages
     * **/
    override fun getAllDirectMessages(): List<DirectMessage> = transaction{
        DirectMessageDb.selectAll()
            .map(::rowToDirectMessage)
            .toList()
    }
}