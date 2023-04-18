package com.components.message.repo

import com.components.message.models.DirectMessage
import com.components.message.models.DirectMessageDTO
import com.components.message.models.DirectMessageMapper
import com.database.DirectMessageDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class DirectMessageDAOImpl : DirectMessageDAO {
    /**
     * fun to convert row to direct message
     * **/
    private fun rowToDirectMessage(row:ResultRow) = DirectMessage(
        id = row[DirectMessageDb.id],
        senderId = row[DirectMessageDb.senderId],
        recipientId = row[DirectMessageDb.recipientId],
        content = row[DirectMessageDb.content],
        timeSent = LocalDateTime.parse(row[DirectMessageDb.timeSent])
    )

    /**
     * function to get a single direct message
     * **/
    override fun getDirectMessage(messageId: String): DirectMessage? = transaction{
        DirectMessageDb.select(DirectMessageDb.id eq messageId)
            .map(::rowToDirectMessage)
            .singleOrNull()
    }

    /**
     * function to create a direct message
     * **/
    override fun createDirectMessage(messageDto: DirectMessageDTO): Int = transaction{
        val message = DirectMessageMapper.toModel(messageDto)
        DirectMessageDb.insert{
            it[id] = message.id
            it[senderId] = message.senderId
            it[recipientId] = message.recipientId
            it[content] = message.content
            it[timeSent] = message.timeSent.toString()
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