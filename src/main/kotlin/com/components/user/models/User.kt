package com.components.user.models

import java.time.LocalDateTime


enum class UserType(val type:String){
    CONSUMER("consumer"),
    SUPPORT("support")
}

data class User(
    val id:String,
    var firstName:String,
    var lastName:String,
    var userName:String,
    var type:String=UserType.CONSUMER.toString(),
    val dateCreated:String= LocalDateTime.now().toString()
)
