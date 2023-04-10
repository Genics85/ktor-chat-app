package com.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object RoomMessageDb: Table("room_message") {
    val id: Column<String> = varchar("id",512)
    val senderId: Column<String> = varchar ("sender_id",512)
    val roomId:Column<String> = varchar("room_id",512)
    val content:Column<String> = varchar("content",2048)
    val timeSent:Column<String> = varchar("time_sent",128)
}