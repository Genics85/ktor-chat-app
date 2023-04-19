package com.components.message.controllers

import com.components.message.models.RoomMessage
import com.components.message.repo.RoomMessageDAO
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.sql.SQLException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RoomMessageDbControllerImplTest {

    private lateinit var service:RoomMessageDAO
    private lateinit var di: DI
    private var factory:PodamFactoryImpl= PodamFactoryImpl()
    private lateinit var underTest:RoomMessageControllerImpl

    @BeforeAll
    fun setUp() {
        service=mockk(relaxed = true)
        di=DI{
            bindSingleton { service }
        }
        underTest= RoomMessageControllerImpl(di)
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getRooMessages() {
        //GIVEN
        var roomId = factory.manufacturePojoWithFullData(String::class.java)
        var messages=factory.manufacturePojoWithFullData(List::class.java, RoomMessage::class.java) as List<RoomMessage>
        every{service.getRoomMessages(any())} returns messages
        //WHEN
        val expected=underTest.getRooMessages(roomId)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun `can not get room messages because room does not exist`(){
        //GIVEN
        val roomId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.getRoomMessages(any())} returns emptyList()
        //WHEN
        val expected = underTest.getRooMessages(roomId)
        //THEN
        assertThat(expected.code).isEqualTo("204")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun `can not get room because of SE error`(){
        //GIVEN
        val roomId=factory.manufacturePojoWithFullData(String::class.java)
        every { service.getRoomMessages(any()) } throws SQLException()
        //WHEN
        val expected = underTest.getRooMessages(roomId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero

    }


    @Test
    fun deleteRoomMessage() {
        //GIVEN
        val messageId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.deleteRoomMessage(any())} returns true
        //WHEN
        val expected=underTest.deleteRoomMessage(messageId)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `can't delete room message because message does not exist`(){
        //GIVEN
        val messageId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.deleteRoomMessage(any())} returns false
        //WHEN
        val expected=underTest.deleteRoomMessage(messageId)
        //THEN
        assertThat(expected.code).isEqualTo("204")
    }

    @Test
    fun `can not delete because of SE error`(){
        //GIVEN
        val messageId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.deleteRoomMessage(any())} throws SQLException()
        //WHEN
        val expected=underTest.deleteRoomMessage(messageId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun createRoomMessage() {
        //GIVEN
        val message=factory.manufacturePojoWithFullData(RoomMessage::class.java)
        every{service.createRoomMessage(any())} returns 1
        //WHEN
        val expected=underTest.createRoomMessage(message)
        //THEN
        assertThat(expected.code).isEqualTo("200")

    }

    @Test
    fun `couldn't create room message because of SQL Exception`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(RoomMessage::class.java)
        every{service.createRoomMessage(any())} throws SQLException()
        //GIVEN
        val expected=underTest.createRoomMessage(message)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun getRoomMessage() {
        //GIVEN
        val message=factory.manufacturePojoWithFullData(RoomMessage::class.java)
        every{service.getRoomMessage(any())} returns message
        //WHEN
        val expected=underTest.getRoomMessage(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isOne
    }

    @Test
    fun `can not get room message because message does not exist`(){
        //GIVEN
        val messageId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.getRoomMessage(any())} returns null
        //WHEN
        val expected=underTest.getRoomMessage(messageId)
        //THEN
        assertThat(expected.code).isEqualTo("204")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun `can not get room message because of SE error`(){
        //GIVEN
        val messageId=factory.manufacturePojoWithFullData(String::class.java)
        every { service.getRoomMessage(any())} throws SQLException()
        //WHEN
        val expected = underTest.getRoomMessage(messageId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun deleteAllRoomMessages() {
        //GIVEN
        val roomId=factory.manufacturePojoWithFullData(String::class.java)
        every { service.deleteAllRoomMessages(any()) } returns true
        //WHEN
        val expected = underTest.deleteAllRoomMessages(roomId)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `can not delete room messages because room does not exist`(){
        //GIVEN
        val roomId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.deleteAllRoomMessages(any())} returns false
        //WHEN
        val expected = underTest.deleteAllRoomMessages(roomId)
        //THEN
        assertThat(expected.code).isEqualTo("204")
    }

    @Test
    fun `can not delete room messages because of SE error`(){
        //GIVEN
        val roomId=factory.manufacturePojoWithFullData(String::class.java)
        every{service.deleteAllRoomMessages(any())} throws SQLException()
        //WHEN
        val expected = underTest.deleteAllRoomMessages(roomId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }
}