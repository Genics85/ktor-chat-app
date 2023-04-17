package com.generis.web

import com.components.user.controllers.UserControllerImpl
import com.components.user.models.User
import com.components.user.models.UserDTO
import com.components.user.repo.UserDAOImpl
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val BASE_URL = "api/users"

fun Routing.userRoutes(){

    val di:DI= DI{
        bindSingleton{ UserDAOImpl() }
    }
    val userInst = UserControllerImpl(di)

    route(BASE_URL){
        get("/get-all") {
            val response = userInst.getAllUsers()
            call.respond(response)
        }

        get("/get/{id}") {
            val userId = call.parameters["id"]?: throw BadRequestException(message = "Request id is undefined")
            val response = userInst.getAUser(userId)
            call.respond(response)
        }
        post("/add"){
            val userInfo = call.receive<UserDTO>()
            val response = userInst.createUser(userInfo)
            call.respond(response)
        }
        post("/user-rooms/{id}"){
            val userId=call.parameters["id"] ?: throw BadRequestException("parameter id can not be null")
            val response=userInst.getUserChatGroups(userId)
            call.respond(response)
        }
        delete("/delete/{id}"){
            val userId=call.parameters["id"] ?: throw BadRequestException("Parameter id can not be null")
            val response=userInst.deleteUser(userId)
            call.respond(response)
        }
        get("/update"){
            val user = call.receive<User>()
            val response = userInst.updateUserDetails(user.id,user.firstName,user.lastName,user.userName)
            call.respond(response)
        }
    }
}