package com.components.user.models

import java.time.LocalDateTime


enum class UserType(val type:String){
    CONSUMER("consumer"),
    SUPPORT("support")
}

data class User(
    val userId:String,
    var firstName:String,
    var lastName:String,
    var userName:String,
    var type:UserType,
    val timeCreated:LocalDateTime
)
