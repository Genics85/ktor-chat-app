package com.config

data class APIResponse<T>(
    val code:String,
    val systemCode:String,
    val message:String,
    val list: List<T>
)
