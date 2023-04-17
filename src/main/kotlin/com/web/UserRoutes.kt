package com.config.plugins

import com.components.user.controllers.UserControllerImpl
import com.components.user.models.User
import com.components.user.models.UserDTO
import com.components.user.models.UserMapper
import com.components.user.repo.UserDAOImpl
import com.config.APIResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val BASE_URL = "api/users"

fun Application.UserRouting(){

    val di:DI= DI{
        bindSingleton{UserDAOImpl()}
    }
   val userController = UserControllerImpl(di)


    routing{
        route(BASE_URL){
            get("get/all"){
                val response = userController.getAllUsers()
                call.respond(response)
            }
            get("get/{id}"){
                val userId = call.parameters["id"]

                if(userId !== null){
                    val response = userController.getAUser(userId)
                    call.respond(response)
                }else{
                    call.respond(HttpStatusCode.NotAcceptable,APIResponse<String>("400","10","You have to provide a user id as a parameter to get a user",listOf()))
                }

            }
            post("/add"){
                val userInfo:UserDTO = call.receive()
                val user = UserMapper.toModel(userInfo)
                val response = userController.createUser(user)
                call.respond(response)
            }
            post("/update"){
                val userInfo = call.receive<User>()
                val response = userController.updateUserDetails(userInfo.id,userInfo.firstName,userInfo.lastName,userInfo.userName)
                call.respond(response)
            }
            delete("/delete"){
                val userId = call.receive<String>()
                val response = userController.deleteUser(userId)
                call.respond(response)
            }
            delete("/get-user-chatrooms/{id}"){
                val userId = call.parameters["id"]
                if(userId !== null){
                    val response = userController.getUserChatGroups(userId)
                    call.respond(response)
                }else(
                        call.respond(HttpStatusCode.NotAcceptable,APIResponse<String>("400","10","You have to add a string id to the url to fetch the chat rooms of a user",listOf()))
                )
            }
        }
    }
}