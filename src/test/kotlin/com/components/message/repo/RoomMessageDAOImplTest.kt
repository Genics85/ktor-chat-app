package com.components.message.repo

import com.components.message.models.RoomMessage
import com.config.DatabaseFactory
import io.ktor.util.reflect.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import uk.co.jemos.podam.api.PodamFactoryImpl

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RoomMessageDAOImplTest {

    private lateinit var messages:List<RoomMessage>
    private lateinit var underTest:RoomMessageDAOImpl
    var factory:PodamFactoryImpl= PodamFactoryImpl()

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
            SchemaUtils.drop(com.database.RoomMessage)
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
        assertThat(expected).isInstanceOf(RoomMessage::class.java)
    }

    @Test
    fun deleteRoomMessage() {
        //GIVEN
        val message = messages.first()
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
        val message = messages.first()
        //WHEN
        val expected = underTest.getMessagesForRoom(message.roomId)
        //THEN
        assertThat(expected.size).isGreaterThan(0)
    }

    @Test
    fun `can not get messages from room because room does not exist`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(RoomMessage::class.java)
        //WHEN
        val expected = underTest.getMessagesForRoom(message.roomId)
        //THEN
        assertThat(expected).isNull()
    }

    @Test
    fun getAllRoomMessages() {
        //GIVEN
        //WHEN
        val expected = underTest.getAllRoomMessages()
        //THEN
        assertThat(expected.size).isGreaterThan(0)
    }
}