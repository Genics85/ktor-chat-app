package com.config

import com.database.ChatRoom
import com.database.DirectMessage
import com.database.RoomMessage
import com.database.User
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DatabaseFactory {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    private fun hikari(): HikariDataSource {
        val config=HikariConfig().apply{
            this.jdbcUrl = "jdbc:mysql://localhost:3306/kotlin_chat_app"
            this.driverClassName = "com.mysql.cj.jdbc.Driver"
            this.username = "root"
            this.password = "TechyGenics85"
            this.isAutoCommit=true
        }
        config.validate()
        return HikariDataSource(config)
    }

    fun connect(){
        val pool= hikari()
        Database.connect(pool)
        transaction{
            SchemaUtils.createMissingTablesAndColumns(User)
            SchemaUtils.createMissingTablesAndColumns(DirectMessage)
            SchemaUtils.createMissingTablesAndColumns(RoomMessage)
            SchemaUtils.createMissingTablesAndColumns(ChatRoom)
        }
        log.info("Database connection was successful")
    }
}