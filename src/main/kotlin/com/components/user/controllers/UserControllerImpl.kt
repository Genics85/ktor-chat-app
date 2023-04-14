package com.components.user.controllers

import com.components.chatRoom.repo.ChatRoomDAO
import com.components.user.models.User
import com.components.user.repo.UserDAO
import com.config.APIResponse
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.sql.SQLException

class UserControllerImpl( override val di: DI) : UserController, DIAware {
    private val userDAO:UserDAO by di.instance()
    private val roomDAO: ChatRoomDAO by di.instance()
    /**
     * function to create user
     * **/
    override fun createUser(user: User): APIResponse<User> {
        val createdUser:APIResponse<User> = try{
            if(user != null){
                var result = userDAO.createUser(user)
                if(result > 0){
                    APIResponse("201","10","User created with success", listOf())
                }else{
                    APIResponse("200","10","User couldn't be created in db",listOf())
                }
            }else{
                APIResponse("404","10","Can't create, user is null",listOf())
            }

        } catch (se:SQLException){
            APIResponse("500","10","Couldn't create user, $se occurred", listOf())
        }
        return  createdUser
    }

    /**
     * function to delete a user
     * **/
    override fun deleteUser(userId: String): APIResponse<Boolean> {
        val userDeleted:APIResponse<Boolean> = try{
            if(userId != null){
                var isDeleted =userDAO.deleteUser(userId)
                if(isDeleted){
                    APIResponse("200","10","user deleted with succes",listOf())
                }else{
                    APIResponse("204","10","User not found to be deleted",listOf())
                }
            }else{
                APIResponse("404","10","Can't delete userid is null",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","10","Couldn't delete $se occurred",listOf())
        }
        return userDeleted
    }

    /**
     * function update user details
     * **/
    override fun updateUserDetails(userId:String,firstName: String?, lastName: String?, userName: String?): APIResponse<Int> {
       val userUpdated:APIResponse<Int> = try {

           var getUser:User? = userDAO.getAUser(userId)

           if(getUser != null){

               var newUser=User(
                   id = getUser.id,
                   firstName = firstName?:getUser.firstName,
                   lastName = lastName?:getUser.lastName,
                   userName = userName?:getUser.userName
               )

               var userUpdated=userDAO.updateUserInfo(newUser)

               if(userUpdated > 0){
                   APIResponse("200","10","User updated successfully",listOf())
               }else{
                   APIResponse("204","10","User didn't get updated",listOf())
               }
           }else{
               APIResponse("404","10","Cant update user because it is null",listOf())
           }
        } catch(se:SQLException){
            APIResponse("500","10","Can't update user because $se occurred",listOf())
        }
        return userUpdated
    }

    /**
     * function to get the groups a user is part of
     * **/
    override fun getUserChatGroups(userId: String): APIResponse<List<String>> {
        val userGroups:APIResponse<List<String>> = try{
            val rooms = roomDAO.getAllChatRooms()
            var userRooms:MutableList<String> = mutableListOf()
            if(rooms.isNotEmpty()){
                rooms.forEach {
                    if(it.membersIDs.contains(userId)){
                        userRooms.add(it.name)
                    }
                }
                if(userRooms.isNotEmpty()){
                    APIResponse("201","10","Got all rooms of user",listOf(userRooms))
                }else{
                    APIResponse("204","10","User does not belong to any room", listOf())
                }
            }else{
                APIResponse("204","10","No room exist on the database",listOf())
            }
        }catch (se:SQLException){
            APIResponse("500","10","Can't get user chat groups because of SE error",listOf())
        }
        return userGroups
    }
}