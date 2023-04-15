package com.components.user.controllers

import com.components.user.models.User
import com.config.APIResponse

interface UserController {
    /**
     * function to create user
     * **/
    fun createUser(user: User):APIResponse<User>
    /**
     * function to delete a user
     * **/
    fun deleteUser(userId:String):APIResponse<Boolean>
    /**
     * function update user details
     * **/
    fun updateUserDetails(userId: String,firstname:String?,lastName:String?,userName:String?):APIResponse<Int>
    /**
     * function to get the groups a user is part of
     * **/
    fun getUserChatGroups(userId:String):APIResponse<List<String>>
    /**
     * function to get a user by id
     * **/
    fun getAUser(userId:String):APIResponse<User>
    /**
     * function to get all users on the platform
     * **/
    fun getAllUsers():APIResponse<List<User>>
}