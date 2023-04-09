package com.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


object UserDb: Table("user") {
    val id:Column<String> = varchar("id",512)
    var firstName:Column<String> = varchar("first_name",512)
    var lastName:Column<String> = varchar("last_name",512)
    var userName:Column<String> = varchar("user_name",512)
    val type:Column<String> = varchar("type",128)
    val dateCreated:Column<String> = varchar("date_created",128)

    override val primaryKey=PrimaryKey(id)

}