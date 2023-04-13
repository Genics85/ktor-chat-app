package com.components.message.repo

import com.components.message.models.RoomMessage
import com.config.DatabaseFactory
import io.ktor.util.reflect.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import uk.co.jemos.podam.api.PodamFactoryImpl

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RoomMessageDbDAOImplTest {

    private lateinit var messages:List<RoomMessage>
    private lateinit var underTest:RoomMessageDAOImpl
    private var factory:PodamFactoryImpl= PodamFactoryImpl()

    @BeforeAll
    fun setup(){
        DatabaseFactory.connect()
        messages=factory.manufacturePojoWithFullData(List::class.java,RoomMessage::class.java) as List<RoomMessage>
        underTest = RoomMessageDAOImpl()
        messages.forEach{
            underTest.createRoomMessage(it)
        }
    }

    @AfterAll
    fun tearDown(){
        transaction{
            SchemaUtils.drop(com.database.RoomMessageDb)
        }
    }

    @Test
    fun createRoomMessage() {
        //GIVEN
        val message=factory.manufacturePojoWithFullData(RoomMessage::class.java)
        //WHEN
        val expected = underTest.createRoomMessage(message)
        //THEN
        assertThat(expected).isNotNull
        assertThat(expected).isGreaterThan(0)
    }

    @Test
    fun deleteRoomMessage() {
        //GIVEN
        val message = underTest.getAllRoomMessages().first()
        //WHEN
        val expected = underTest.deleteRoomMessage(message.id)
        //THEN
        assertThat(expected).isTrue
    }

    @Test
    fun `can not delete room message because it does not exist`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(RoomMessage::class.java)
        //WHEN
        val expected = underTest.deleteRoomMessage(message.id)
        //THEN
        assertThat(expected).isFalse
    }

    @Test
    fun getMessagesForRoom() {
        //GIVEN
        val message = underTest.getAllRoomMessages().first()
        //WHEN
        val expected = underTest.getRoomMessages(message.roomId)
        //THEN
        assertThat(expected.size).isGreaterThan(0)
        assertThat(expected.first()).isInstanceOf(RoomMessage::class.java)
    }

    @Test
    fun `can not get messages from room because room does not exist`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(RoomMessage::class.java)
        //WHEN
        val expected = underTest.getRoomMessages(message.roomId)
        //THEN
        assertThat(expected.isEmpty())
    }

    @Test
    fun getAllRoomMessages() {
        //GIVEN
        //WHEN
        val expected = underTest.getAllRoomMessages()
        //THEN
        assertThat(expected.size).isGreaterThan(0)
        assertThat(expected.first()).isInstanceOf(RoomMessage::class.java)
    }

    @Test
    fun getRoomMessage(){
        //GIVEN
        val message=underTest.getAllRoomMessages().first()
        //WHEN
        val expected = underTest.getRoomMessage(message.id)
        //THEN
        assertThat(expected).isEqualTo(message)
    }

    @Test
    fun `can not get room message because id does not exist`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(RoomMessage::class.java)
        //WHEN
        val expected=underTest.getRoomMessage(message.id)
        //THEN
        assertThat(expected).isNull()
    }

    @Test
    fun deleteAllRoomMessages(){
        //GIVEN
        val message=messages.first()
        //WHEN
        val expected= underTest.deleteAllRoomMessages(message.roomId)
        //THEN
        assertThat(expected).isTrue
    }

    @Test
    fun `can not delete room messages because room does not exist`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(RoomMessage::class.java)
        //WHEN
        val expected = underTest.deleteAllRoomMessages(message.roomId)
        //THEN
        assertThat(expected).isFalse
    }
}