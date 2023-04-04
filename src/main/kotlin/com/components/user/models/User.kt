package com.components.user.models

import java.time.LocalDateTime


enum class UserType(val type:String){
    CONSUMER("consumer"),
    SUPPORT("support")
}

data class User(
    val _id:String,
    var firstName:String,
    var lastName:String,
    var userName:String,
    var type:UserType=UserType.CONSUMER,
    val dateCreated:LocalDateTime= LocalDateTime.now()
)
