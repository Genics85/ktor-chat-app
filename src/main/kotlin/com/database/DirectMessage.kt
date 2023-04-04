package com.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DirectMessage: Table("direct_message") {
    val id: Column<String> = varchar("id",512)
    val senderId:Column<String> = varchar("sender_id",512)
    val recipientId:Column<String> = varchar("recipient_id",512)
    val content:Column<String> = varchar("content",2048)
    val timeSent:Column<String> = varchar("time_sent",128)

    override val primaryKey=PrimaryKey(id)
}