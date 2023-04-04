package com.config

import com.components.chatRoom.repo.ChatRoomDAOImpl
import com.components.message.repo.DirectMessageDAOImpl
import com.components.message.repo.RoomMessageDAOImpl
import com.components.user.repo.UserDAOImpl
import io.ktor.server.application.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun Application.configureDI(){
    DI{
        bindSingleton { UserDAOImpl()}
        bindSingleton { ChatRoomDAOImpl()}
        bindSingleton { DirectMessageDAOImpl()}
        bindSingleton { RoomMessageDAOImpl()}
    }
}
