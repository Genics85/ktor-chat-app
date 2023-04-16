package com.config.plugins

import com.components.user.controllers.UserControllerImpl
import com.components.user.models.UserDTO
import com.components.user.models.UserMapper
import com.components.user.repo.UserDAOImpl
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
                val allUsers =response.data
                if(allUsers.isNotEmpty()){
                    call.respond(HttpStatusCode.Found,allUsers)
                }else{
                    call.respond(HttpStatusCode.NotFound,"Users database is empty")
                }
            }
            get("get/{id}"){
                val userId = call.parameters["id"]
                val user = userId?.let { it1 -> userController.getAUser(it1) }
                if(user !==null){
                    call.respond(HttpStatusCode.Found,user)
                }else{
                    call.respond(HttpStatusCode.NotFound,"User ID not provided in param")
                }
            }
            post("/add"){
                val userInfo:UserDTO = call.receive()
                val user = UserMapper.toModel(userInfo)
                val response = userController.createUser(user)
                if(response.code =="200"){
                    call.respond(HttpStatusCode.Created,"User created with success")
                }else if(response.code == "204"){
                    call.respond(HttpStatusCode.NotAcceptable,"User could not be created")
                }
            }
        }
    }
}