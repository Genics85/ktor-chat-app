package com.components.message.repo

import com.components.message.models.DirectMessage
import com.config.DatabaseFactory
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
internal class DirectMessageDbDAOImplTest {
    private lateinit var underTest:DirectMessageDAOImpl
    private lateinit var messages:List<DirectMessage>
    private var factory:PodamFactoryImpl = PodamFactoryImpl()

    @BeforeAll
    fun setup(){
        DatabaseFactory.connect()
        underTest= DirectMessageDAOImpl()
        messages = factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        messages.forEach{
            underTest.createDirectMessage(it)
        }
    }

    @AfterAll
    fun tearDown(){
        transaction{
            SchemaUtils.drop(com.database.DirectMessageDb)
        }
    }

    @Test
    fun createDirectMessage() {
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        //WHEN
        val expected=underTest.createDirectMessage(message)
        //THEN
        assertThat(expected).isInstanceOf(DirectMessage::class.java)
        assertThat(expected).isNotNull
    }

    @Test
    fun deleteDirectMessage() {
        //GIVEN
        val message = messages.first()
        //WHEN
        val expected=underTest.deleteDirectMessage(message.id)
        //THEN
        assertThat(expected).isTrue
    }

    @Test
    fun `can not delete because message id does not exist`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        //WHEN
        val expected=underTest.deleteDirectMessage(message.id)
        //THEN
        assertThat(expected).isFalse
    }

    @Test
    fun getDirectMessagesForRecipient() {
        //GIVEN
        val message = messages.first()
        //WHEN
        val expected = underTest.getDirectMessagesForRecipient(message.recipientId)
        //THEN
        assertThat(expected.first()).isInstanceOf(DirectMessage::class.java)
        assertThat(expected.size).isGreaterThan(0)
    }

    @Test
    fun `can not get direct message because there is no message for recipient`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        //WHEN
        val expected=underTest.getDirectMessagesForRecipient(message.recipientId)
        //THEN
        assertThat(expected.size).isEqualTo(0)
    }


    @Test
    fun getDirectMessageFromSender() {
        //GIVEN
        val message = messages.first()
        //WHEN
        val expected = underTest.getDirectMessageFromSender(message.senderId)
        //THEN
        assertThat(expected.first()).isInstanceOf(DirectMessage::class.java)
        assertThat(expected.size).isGreaterThan(0)
    }

    @Test
    fun `can not get direct message because there is no message from sender`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        //WHEN
        val expected=underTest.getDirectMessagesForRecipient(message.senderId)
        //THEN
        assertThat(expected.size).isEqualTo(0)
    }

    @Test
    fun getAllDirectMessages() {
        //GIVEN
        //WHEN
        val expected = underTest.getAllDirectMessages()
        //THEN
        assertThat(expected.size).isGreaterThan(0)
        assertThat(expected.first()).isInstanceOf(DirectMessage::class.java)
    }
}