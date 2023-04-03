package com.components.user.repo

import com.components.user.models.User

interface UserDAO {
    /**
     * function to create a new user
     * **/
    fun createUser(user:User):User
    /**
     * function to delete a user
     * **/
    fun deleteUser(id:String):Boolean
    /**
     * function to get a user
     * **/
    fun getAUser(id:String):User?
    /**
     * function to get all users
     **/
    fun getAllUsers():List<User>
}