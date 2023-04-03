package com.components.user.models

import java.time.LocalDateTime


enum class UserType(val type:String){
    CONSUMER("consumer"),
    SUPPORT("support")
}

data class User(
    val userId:String,
    val firstName:String,
    val lastName:String,
    val userName:String,
    val type:UserType,
    val timeCreated:LocalDateTime
)
