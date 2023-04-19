package com.components.user.repo

import com.components.user.models.User
import com.components.user.models.UserDTO
import com.config.DatabaseFactory
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
internal class UserDbDAOImplTest {

    private lateinit var underTest:UserDAOImpl
    private val factory:PodamFactoryImpl= PodamFactoryImpl()
    private lateinit var users:List<UserDTO>
//    private var log=LoggerFactory.getLogger(this::class.java)

    @BeforeAll
    fun setup(){
        DatabaseFactory.connect()
        underTest=UserDAOImpl()
        users= factory.manufacturePojoWithFullData(List::class.java,UserDTO::class.java) as List<UserDTO>
        users.forEach{
            underTest.createUser(it)
        }
    }
    @AfterAll
    fun tearDown(){
        transaction{
            SchemaUtils.drop(com.database.UserDb)
        }
    }

    @Test
    fun createUser() {
        //GIVEN
        val user=factory.manufacturePojoWithFullData(UserDTO::class.java)
        //WHEN
        val expected =underTest.createUser(user)
        //THEN
        assertThat(expected).isGreaterThan(0)
    }

    @Test
    fun deleteUser() {
        //GIVEN
        val user=underTest.getAllUsers().first()
        //WHEN
        val expected=underTest.deleteUser(user.id)
        //THEN
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
        val user=underTest.getAllUsers().first()
        //WHEN
        val expected=underTest.getAUser(user.id)
        //THEN
        assertThat(expected).isNotNull
        assertThat(expected).isInstanceOf(User::class.java)
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
        val user=underTest.getAllUsers().first()
        user.firstName="Nsano"
        user.lastName="Fintech"
        //WHEN
        val expected=underTest.updateUserInfo(user)
        //THEN
        assertThat(expected).isGreaterThan(0)
        assertThat(expected).isNotNull
    }
    @Test
    fun `can't update user because id does not exist`(){
        //GIVEN
        val user=factory.manufacturePojoWithFullData(User::class.java)
        user.firstName="wont change"
        user.userName="vim"
        //WHEN
        val expected=underTest.updateUserInfo(user)
        //THEN
        assertThat(expected).isEqualTo(0)
    }
}