package com.components.message.controllers

import com.components.message.models.DirectMessage
import com.components.message.repo.DirectMessageDAO
import com.components.message.repo.DirectMessageDAOImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import uk.co.jemos.podam.api.PodamFactoryImpl

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
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isOne
    }

    @Test
    fun getDirectMessagesForRecipient() {
        //GIVEN
        val messages=factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every{service.getDirectMessagesForRecipient(any())} returns messages
        //WHEN
        val expected = underTest.getDirectMessagesForRecipient(messages.first().recipientId)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun getDirectMessagesFromSender() {
        //GIVEN
        val messages=factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every{service.getDirectMessageFromSender(any())} returns messages
        //WHEN
        val expected = underTest.getDirectMessagesFromSender(messages.first().senderId)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)
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
    fun deleteDirectMessage() {
        //GIVEN
        val message = factory.manufacturePojoWithFullData(DirectMessage::class.java)
        every{service.deleteDirectMessage(any())} returns true
        //WHEN
        val expected = underTest.deleteDirectMessage(message.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
    }

    @Test
    fun getAllDirectMessages() {
        //GIVEN
        val messages = factory.manufacturePojoWithFullData(List::class.java,DirectMessage::class.java) as List<DirectMessage>
        every { service.getAllDirectMessages() } returns messages
        //WHEN
        val expected =underTest.getAllDirectMessages()
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)

    }
}