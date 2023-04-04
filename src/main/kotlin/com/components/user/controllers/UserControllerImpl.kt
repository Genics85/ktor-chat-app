package com.components.user.controllers

import com.components.user.models.User
import com.config.APIResponse

class UserControllerImpl : UserController {
    /**
     * function to create user
     * **/
    override fun createUser(user: User): APIResponse<User> {
        TODO("Not yet implemented")
    }

    /**
     * function to delete a user
     * **/
    override fun deleteUser(userId: String): APIResponse<Boolean> {
        TODO("Not yet implemented")
    }

    /**
     * function update user details
     * **/
    override fun updateUserDetails(firstname: String?, lastName: String?, userName: String?): APIResponse<Int> {
        TODO("Not yet implemented")
    }

    /**
     * function to get the groups a user is part of
     * **/
    override fun getUserChatGroups(userId: String): APIResponse<List<String>> {
        TODO("Not yet implemented")
    }
}