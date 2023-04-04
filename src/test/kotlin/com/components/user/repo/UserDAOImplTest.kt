package com.components.user.repo

import com.components.user.models.User
import com.config.DatabaseFactory
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import uk.co.jemos.podam.api.PodamFactoryImpl

internal class UserDAOImplTest {

    private lateinit var underTest:UserDAOImpl
    val factory:PodamFactoryImpl= PodamFactoryImpl()
    private lateinit var users:List<User>

    @BeforeAll
    fun setup(){
        DatabaseFactory.connect()
        underTest=UserDAOImpl()
        users= factory.manufacturePojoWithFullData(List::class.java,User::class.java) as List<User>
        users.forEach{
            underTest.createUser(it)
        }
    }
    @AfterAll
    fun tearDown(){
        transaction{
            SchemaUtils.drop(com.database.User)
        }
    }

    @Test
    fun createUser() {
    }

    @Test
    fun deleteUser() {
    }

    @Test
    fun getAUser() {
    }

    @Test
    fun getAllUsers() {
    }

    @Test
    fun updateUserInfo() {
    }
}