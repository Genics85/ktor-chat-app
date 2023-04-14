package com.components.chatRoom.repo

import com.components.chatRoom.models.ChatRoom
import com.config.DatabaseFactory
import com.database.ChatRoomDb
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
internal class ChatRoomDAOImplTest {

    private lateinit var underTest:ChatRoomDAOImpl
    private lateinit var rooms:List<ChatRoom>
    private var factory:PodamFactoryImpl= PodamFactoryImpl()

    @BeforeAll
    fun setup(){
        DatabaseFactory.connect()
        underTest= ChatRoomDAOImpl()
        rooms=factory.manufacturePojoWithFullData(List::class.java,ChatRoom::class.java) as List<ChatRoom>
        rooms.forEach{
            underTest.createChatRoom(it)
        }
    }

    @AfterAll
    fun tearDown(){
        transaction{
            SchemaUtils.drop(ChatRoomDb)
        }
    }

    @Test
    fun getChatRoom() {
        //GIVEN
        val room=underTest.getAllChatRooms().first()
        //WHEN
        val expected = underTest.getChatRoom(room.id)
        //THEN
        assertThat(expected).isInstanceOf(ChatRoom::class.java)
    }

    @Test
    fun `can not get chat room because it does not exist`(){
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        //WHEN
        val expected = underTest.getChatRoom(room.id)
        //THEN
        assertThat(expected).isNull()
    }

    @Test
    fun createChatRoom() {
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        //WHEN
        val expected = underTest.createChatRoom(room)
        //THEN
        assertThat(expected).isOne
    }

    @Test
    fun deleteChatRoom() {
        //GIVEN
        val room =rooms.first()
        //WHEN
        val expected = underTest.deleteChatRoom(room.id)
        //THEN
        assertThat(expected).isTrue
    }

    @Test
    fun `can not delete chat room because it does nto exist`(){
        //GIVEN
        val room = factory.manufacturePojoWithFullData(ChatRoom::class.java)
        //WHEN
        val expected = underTest.deleteChatRoom(room.id)
        //THEN
        assertThat(expected).isFalse
    }

    @Test
    fun deleteUserFromChatRoom() {
        //GIVEN
        val room=underTest.getAllChatRooms().first()
        //WHEN
        val expected = underTest.deleteUserFromChatRoom(room.id,listOf(room.membersIDs[2],room.membersIDs[4],room.membersIDs[3]))
        //THEN
        assertThat(expected).isTrue
    }

    @Test
    fun `can not delete user from chat room because user is not part of group`(){
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        //WHEN
        val expected = underTest.deleteUserFromChatRoom(room.id,room.membersIDs)
        //THEN
        assertThat(expected).isFalse
    }

    @Test
    fun addUserToChatRoom() {
        //GIVEN
        val userIds = factory.manufacturePojoWithFullData(List::class.java,String::class.java) as List<String>
        val room=underTest.getAllChatRooms().first()
        //WHEN
        val expected = underTest.addUserToChatRoom(room.id,userIds)
        //THEN
        assertThat(expected).isTrue
    }
    @Test
    fun changeChatRoomName() {
        //GIVEN
        val room=underTest.getAllChatRooms().first()
        room.name="somethinggreat"
        //WHEN
        val expected = underTest.changeChatRoomName(room.id,room.name)
        //THEN
        assertThat(expected).isGreaterThan(0)
    }
    @Test
    fun `can not change room name because room does not exist`(){
        //GIVEN
        val room=factory.manufacturePojoWithFullData(ChatRoom::class.java)
        room.name="sokode"
        //WHEN
        val expected=underTest.changeChatRoomName(room.id,room.name)
        //THEN
        assertThat(expected).isZero
    }

}