package com.components.user.controllers

import com.components.chatRoom.models.ChatRoom
import com.components.chatRoom.repo.ChatRoomDAO
import com.components.user.models.User
import com.components.user.repo.UserDAO
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.sql.SQLException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserDbControllerImplTest {
    private lateinit var underTest:UserControllerImpl
    private lateinit var service:UserDAO
    private lateinit var roomService:ChatRoomDAO
    private lateinit var di: DI
    private var factory:PodamFactoryImpl= PodamFactoryImpl()

    @BeforeAll
    fun setup(){
        service=mockk(relaxed = true)
        roomService=mockk(relaxed = true)
        di=DI{
            bindSingleton { service }
        }
        underTest= UserControllerImpl(di)
    }

    @AfterAll
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun createUser() {
        //GIVEN
        val user = factory.manufacturePojoWithFullData(User::class.java)
        every { service.createUser(any())} returns 1
        //WHEN
        val expected = underTest.createUser(user)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isOne
    }

    @Test
    fun `couldn't create user because of SE error`(){
        //GIVEN
        val user = factory.manufacturePojoWithFullData(User::class.java)
        every { service.createUser(any())} throws SQLException()
        //WHEN
        val expected = underTest.createUser(user)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun deleteUser() {
        //GIVEN
        val userId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.deleteUser(any())} returns true
        //WHEN
        val expected = underTest.deleteUser(userId)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `couldn't delete user because user is not found `(){
        //GIVEN
        val userId=factory.manufacturePojoWithFullData(String::class.java)
        every { service.deleteUser(any())} returns false
        //WHEN
        val expected = underTest.deleteUser(userId)
        //THEN
        assertThat(expected.code).isEqualTo("204")
    }

    @Test
    fun `can not delete because of SE error`(){
        //GIVEN
        val userId=factory.manufacturePojoWithFullData(String::class.java)
        every { service.deleteUser(any()) } throws SQLException()
        //WHEN
        val expected = underTest.deleteUser(userId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun updateUserDetails() {
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        user.lastName="Abrante3"
        user.firstName="Genics"
        every{service.updateUserInfo(any(),any(),any(),null)} returns 2
        //WHEN
        val expected = underTest.updateUserDetails(user.id,user.firstName,user.lastName)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `cant update user details because user does not exist`(){
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        user.lastName="Abrante3"
        user.firstName="Genics"
        every { service.updateUserInfo(any(),any(),any(),null) } returns 0
        //WHEN
        val expected = underTest.updateUserDetails(user.id,user.firstName,user.lastName)
        //THEN
        assertThat(expected.code).isEqualTo("204")
    }
    @Test
    fun `can not update because of SE error`(){
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        user.lastName="Abrante3"
        user.firstName="Genics"
        every { service.updateUserInfo(any(),any(),any(),null) } throws SQLException()
        //WHEN
        val expected = underTest.updateUserDetails(user.id,user.firstName,user.lastName)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun getUserChatGroups() {
        //GIVEN
        val userId=factory.manufacturePojoWithFullData(String::class.java)
        val rooms=factory.manufacturePojoWithFullData(List::class.java,ChatRoom::class.java) as List<ChatRoom>
        every { roomService.getAllChatRooms() } returns rooms
        //WHEN
        val expected = underTest.getUserChatGroups(userId)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun `can not get any group because user is not part of any group`(){
        //GIVEN
        val userId=factory.manufacturePojoWithFullData(String::class.java)
        every { roomService.getAllChatRooms() } returns listOf()
        //WHEN
        val expected = underTest.getUserChatGroups(userId)
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun `can not get user groups because of SE error`(){
        //GIVEN
        val userId=factory.manufacturePojoWithFullData(String::class.java)
        every{roomService.getAllChatRooms()} throws SQLException()
        //WHEN
        val expected=underTest.getUserChatGroups(userId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero
    }
}