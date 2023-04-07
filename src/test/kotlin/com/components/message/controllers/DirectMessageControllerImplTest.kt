package com.components.message.controllers

import com.components.message.models.DirectMessage
import com.components.message.repo.DirectMessageDAO
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.sql.SQLException

internal class DirectMessageControllerImplTest {

    private lateinit var underTest:DirectMessageControllerImpl
    private lateinit var service: DirectMessageDAO
    private lateinit var di: DI
    private var factory:PodamFactoryImpl= PodamFactoryImpl()

    @BeforeAll
    fun setUp() {
        service = mockk( relaxed = true)
        di=DI{
            bindSingleton {service}
        }
        underTest= DirectMessageControllerImpl(di)
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getDirectMessage() {
        //GIVEN
        val messages=factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every{service.getAllDirectMessages()} returns messages
        //WHEN
        val expected = underTest.getDirectMessage(messages.first().id)
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isOne
    }

    @Test
    fun `Didn't get any direct message because direct message is not found`(){
        //GIVEN
        val messages=factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every{service.getAllDirectMessages()} returns messages
        //WHEN
        val expected =underTest.getDirectMessage("loremipsumtext")
        //THEN
        assertThat(expected.code).isEqualTo("204")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun `Didn't get any direct message because of SE error`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every { service.getAllDirectMessages() } throws SQLException()
        //WHEN
        val expected = underTest.getDirectMessage(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun getDirectMessagesForRecipient() {
        //GIVEN
        val messages=factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every{service.getDirectMessagesForRecipient(any())} returns messages
        //WHEN
        val expected = underTest.getDirectMessagesForRecipient(messages.first().recipientId)
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun `There is no message for recipient`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every { service.getDirectMessagesForRecipient(any()) } returns listOf()
        //WHEN
        val expected = underTest.getDirectMessagesForRecipient(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("204")
    }

    @Test
    fun `can not retrieve message for recipient because of SE error`(){
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every{service.getDirectMessagesForRecipient(any())} throws SQLException()
        //WHEN
        val expected = underTest.getDirectMessagesForRecipient(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun getDirectMessagesFromSender() {
        //GIVEN
        val messages=factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every{service.getDirectMessageFromSender(any())} returns messages
        //WHEN
        val expected = underTest.getDirectMessagesFromSender(messages.first().senderId)
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun `no sender messages found in db`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every { service.getDirectMessageFromSender(any()) } returns listOf()
        //WHEN
        val expected = underTest.getDirectMessagesFromSender(message.senderId)
        //THEN
        assertThat(expected.code).isEqualTo("204")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun ` cant retrieve message from sender for SE error`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every { service.getDirectMessageFromSender(any()) } throws SQLException()
        //WHEN
        val expected = underTest.getDirectMessagesFromSender(message.senderId)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun createDirectMessage() {
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every{service.createDirectMessage(any())} returns message
        //WHEN
        val expected = underTest.createDirectMessage(message)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isOne
    }

    @Test
    fun `failed to create direct message`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every { service.createDirectMessage(any()) } returns null
        //WHEN
        val expected=underTest.createDirectMessage(message)
        //THEN
        assertThat(expected.code).isEqualTo("204")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun `failed to create direct message because of SE error`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every { service.createDirectMessage(any()) } throws SQLException()
        //WHEN
        val expected = underTest.createDirectMessage(message)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun deleteDirectMessage() {
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every{service.deleteDirectMessage(any())} returns true
        //WHEN
        val expected = underTest.deleteDirectMessage(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `can not delete direct message because it does not exist`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every{service.deleteDirectMessage(any())} returns false
        //WHEN
        val expected = underTest.deleteDirectMessage(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `can not delete direst message because of SE error`(){
        //GIVEN
        val message=factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every{service.deleteDirectMessage(any())} throws SQLException()
        //WHEN
        val expected = underTest.deleteDirectMessage(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun getAllDirectMessages() {
        //GIVEN
        val messages = factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every { service.getAllDirectMessages() } returns messages
        //WHEN
        val expected =underTest.getAllDirectMessages()
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun `can not get any direct message because some does not exist in the db`(){
        //GIVEN
        every{service.getAllDirectMessages()} returns listOf()
        //WHEN
        val expected = underTest.getAllDirectMessages()
        //THEN
        assertThat(expected.code).isEqualTo("204")
        assertThat(expected.data.size).isZero
    }

    @Test
    fun `can not get direct messages because SE error`(){
        //GIVEN
        every { service.getAllDirectMessages() } throws SQLException()
        //WHEN
        val expected = underTest.getAllDirectMessages()
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isZero
    }
}