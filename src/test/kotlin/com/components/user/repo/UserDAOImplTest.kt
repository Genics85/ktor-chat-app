package com.components.user.repo

import com.components.user.models.User
import com.config.DatabaseFactory
import org.assertj.core.api.AssertionsForClassTypes.assertThat
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
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        //WHEN
        val expected =underTest.createUser(user)
        //THEN
        assertThat(expected).isInstanceOf(User::class.java)
        assertThat(expected).isNotNull
    }

    @Test
    fun deleteUser() {
        //GIVEN
        val user=users.first()
        //WHEN
        val expected=underTest.deleteUser(user.id)
        //THEN
        assertThat(expected).isInstanceOf(Boolean::class.java)
        assertThat(expected).isTrue
    }
    @Test
    fun `can't delete user because id does not exist`(){
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        //WHEN
        val expected=underTest.deleteUser(user.id)
        //THEN
        assertThat(expected).isFalse
    }

    @Test
    fun getAUser() {
        //GIVEN
        val user=users.first()
        //WHEN
        val expected=underTest.getAUser(user.id)
        //THEN
        assertThat(expected).isInstanceOf(user::class.java)
        assertThat(expected).isNotNull
    }

    @Test
    fun `can't get a user because Id does not exist`(){
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        //WHEN
        val expected=underTest.getAUser(user.id)
        //THEN
        assertThat(expected).isNull()
    }

    @Test
    fun getAllUsers() {
        //GIVEN
        //WHEN
        val expected=underTest.getAllUsers()
        //THEN
        assertThat(expected).isInstanceOf(List::class.java)
        assertThat(expected.first()).isInstanceOf(User::class.java)
        assertThat(expected).isNotNull
    }

    @Test
    fun updateUserInfo() {
        //GIVEN
        val user=users.first()
        user.firstName="Nsano"
        user.lastName="Fintech"
        //WHEN
        val expected=underTest.updateUserInfo(user.id,user.firstName,user.lastName,null)
        //THEN
        assertThat(expected).isEqualTo(2)
        assertThat(expected).isInstanceOf(Int::class.java)
        assertThat(expected).isNotNull
    }
    @Test
    fun `can't update user because id does not exist`(){
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        user.firstName="wont change"
        user.userName="vim"
        //WHEN
        val expected=underTest.updateUserInfo(user.id,user.firstName,null,user.userName)
        //THEN
        assertThat(expected).isEqualTo(0)
    }
}