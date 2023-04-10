package com.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ChatRoomDb: Table("chat_room") {
    val id: Column<String> = varchar("id",512)
    var membersIDs:Column<String> = varchar("members_ids",512)
    val chatInitiator:Column<String> = varchar("chat_initiator",512)
    var roomName:Column<String> = varchar("room_name",512)
    val dateCreated:Column<String> = varchar("date_created",128)

    override val primaryKey =PrimaryKey(id)
}