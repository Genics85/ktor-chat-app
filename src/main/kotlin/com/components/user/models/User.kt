package com.components.user.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime


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

data class UserDTO(
    var firstName: String,
    var lastName: String,
    var userName:String,
)
