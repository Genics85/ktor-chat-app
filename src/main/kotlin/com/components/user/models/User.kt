package com.components.user.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*


enum class UserType{
    CONSUMER,
    SUPPORT
}
@Serializable
data class User(
    val id:String,
    var firstName:String,
    var lastName:String,
    var userName:String,
    var type:UserType=UserType.CONSUMER,
    val dateCreated:LocalDateTime = LocalDateTime.now()
)

@Serializable
data class UserDTO(
    var firstName: String,
    var lastName: String,
    var userName:String,
)

/**
 * mapper object contains functions to convert model to and from user and dto
 * **/
object UserMapper {
    fun toModel(userDTO:UserDTO):User{
        return User(
            id = UUID.randomUUID().toString(),
            firstName = userDTO.firstName,
            lastName = userDTO.lastName,
            userName = userDTO.userName,
            dateCreated = LocalDateTime.now()
        )
    }

    fun toDTO(user:User):UserDTO{
        return UserDTO(
            firstName = user.firstName,
            lastName = user.lastName,
            userName = user.userName,
        )
    }

}
