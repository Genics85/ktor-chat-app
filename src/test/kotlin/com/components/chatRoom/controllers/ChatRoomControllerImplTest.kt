package com.components.chatRoom.controllers

import com.components.chatRoom.models.ChatRoom
import com.components.chatRoom.repo.ChatRoomDAO
import com.components.message.models.RoomMessage
import com.components.message.repo.RoomMessageDAO
import io.mockk.every
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import uk.co.jemos.podam.api.PodamFactoryImpl
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import java.sql.SQLException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChatRoomControllerImplTest {

    private lateinit var underTest:ChatRoomControllerImpl
    private lateinit var roomService:ChatRoomDAO
    private lateinit var messageService:RoomMessageDAO
    private lateinit var di:DI
    private var factory:PodamFactoryImpl= PodamFactoryImpl()


    @BeforeAll
    fun setup(){
        roomService=mockk(relaxed = true)
        messageService=mockk(relaxed = true)
        di=DI{
            bindSingleton{roomService}
            bindSingleton {messageService}
        }
        underTest=ChatRoomControllerImpl(di)
    }

    @AfterAll
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun createChatRoom() {
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every{roomService.createChatRoom(any())} returns room
        //WHEN
        val expected=underTest.createChatRoom(room)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isEqualTo(1)
    }

    @Test
    fun `create chat room not successful for sql exception`(){
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every{roomService.createChatRoom(any())} throws SQLException()
        //WHEN
        val expected = underTest.createChatRoom(room)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isEqualTo(0)
    }

    @Test
    fun `can not create chat room`(){
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.createChatRoom(any()) } returns null
        //WHEN
        val expected=underTest.createChatRoom(room)
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isEqualTo(0)
    }

    @Test
    fun getChatRoom() {
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.getChatRoom(any()) } returns room
        //WHEN
        val expected = underTest.getChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isEqualTo(1)
    }

    @Test
    fun `can not get chat room because of sql exception error`(){
        //GIVEN
        val room =factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.getChatRoom(any()) } throws SQLException()
        //WHEN
        val expected = underTest.getChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("500")
        assertThat(expected.data.size).isEqualTo(0)
    }

    @Test
    fun `can not get chat because it does not exist`(){
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every{roomService.getChatRoom(any())} returns null
        //WHEN
        val expected=underTest.getChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("200")
        assertThat(expected.data.size).isEqualTo(0)
    }

    @Test
    fun deleteChatRoom() {
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every{roomService.deleteChatRoom(any())} returns true
        //WHEN
        val expected = underTest.deleteChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
    }

    @Test
    fun `can not delete room because id does not exist`(){
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.deleteChatRoom((any())) } returns false
        //WHEN
        val expected = underTest.deleteChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("200")
    }

    @Test
    fun `can not delete because of SQL Exception`(){
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.deleteChatRoom(any()) } throws SQLException()
        //WHEN
        val expected = underTest.deleteChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("500")
    }

    @Test
    fun getChatRoomMessages() {
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        val roomMessages = factory.manufacturePojoWithFullData(List::class.java,RoomMessage::class.java) as List<RoomMessage>
        every{messageService.getRoomMessages(any())} returns roomMessages
        //WHEN
        val expected = underTest.getChatRoomMessages(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)
    }

    @Test
    fun changeChatRoomName() {
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every{roomService.changeChatRoomName(any(),any())} returns true
        //WHEN
        val expected = underTest.changeChatRoomName(room.id,"something new")
        //THEN
        assertThat(expected.code).isEqualTo("201")
    }

    @Test
    fun addUserToChatRoom() {
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every{roomService.addUserToChatRoom(any(),any())} returns true
        //WHEN
        val expected = underTest.addUserToChatRoom(room.id, listOf("sdkfjlskjflskd"))
        //THEN
        assertThat(expected.code).isEqualTo("201")
    }

    @Test
    fun deleteUsersFromChatRoom() {
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.deleteChatRoom(any()) } returns true
        //WHEN
        val expected = underTest.deleteChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
    }

    @Test
    fun getMembersOfChatRoom() {
        //GIVEN
        val room =factory.manufacturePojoWithFullData(ChatRoom::class.java)
        every { roomService.getChatRoom(any()) } returns room
        //WHEN
        val expected = underTest.getMembersOfChatRoom(room.id)
        //THEN
        assertThat(expected.code).isEqualTo("201")
        assertThat(expected.data.size).isGreaterThan(0)

    }


}