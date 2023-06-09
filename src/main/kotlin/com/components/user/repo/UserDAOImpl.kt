package com.components.user.repo

import com.components.user.models.User
import com.components.user.models.UserDTO
import com.components.user.models.UserMapper
import com.database.UserDb
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class UserDAOImpl : UserDAO {
    /**
     * function to convert result row to User
     * **/
    private fun rowToUser(row: ResultRow)= User(
        id = row[UserDb.id],
        firstName = row[UserDb.firstName],
        lastName = row[UserDb.lastName],
        userName = row[UserDb.userName],
        type = enumValueOf(row[UserDb.type]),
        dateCreated = LocalDateTime.parse( row[UserDb.dateCreated])
    )
    /**
     * function to create a new user
     * **/
    override fun createUser(userDTO: UserDTO): Int = transaction{
        val user = UserMapper.toModel(userDTO)
        UserDb.insert {
            it[id] = user.id
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[userName] = user.userName
            it[type] = user.type.toString()
            it[dateCreated] = user.dateCreated.toString()
        }.insertedCount
    }

    /**
     * function to delete a user
     * **/
    override fun deleteUser(id: String): Boolean = transaction {
        UserDb.deleteWhere { UserDb.id eq id } > 0
    }

    /**
     * function to get a user
     * **/
    override fun getAUser(id: String): User? = transaction{
        UserDb.select( UserDb.id eq id)
            .map(::rowToUser)
            .singleOrNull()
    }

    /**
     * function to get all users
     **/
    override fun getAllUsers(): List<User> = transaction{
        UserDb.selectAll()
            .map(::rowToUser)
            .toList()
    }

    /**
     * update user information
     * **/
    override fun updateUserInfo(user:User): Int = transaction{
        UserDb.update ({UserDb.id eq user.id}){
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[userName] = user.userName
        }
    }
}