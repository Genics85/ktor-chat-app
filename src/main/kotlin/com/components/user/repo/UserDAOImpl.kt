package com.components.user.repo

import com.components.user.models.User

class UserDAOImpl : UserDAO {
    /**
     * function to create a new user
     * **/
    override fun createUser(user: User): User {
        TODO("Not yet implemented")
    }

    /**
     * function to delete a user
     * **/
    override fun deleteUser(id: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * function to get a user
     * **/
    override fun getAUser(id: String): User? {
        TODO("Not yet implemented")
    }

    /**
     * function to get all users
     **/
    override fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    /**
     * update user information
     * **/
    override fun updateUserInfo(firstname: String?, lastName: String?, userName: String?): Int {
        TODO("Not yet implemented")
    }
}