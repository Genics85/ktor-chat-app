package com.config

import com.components.chatRoom.controllers.ChatRoomControllerImpl
import com.components.chatRoom.repo.ChatRoomDAO
import com.components.chatRoom.repo.ChatRoomDAOImpl
import com.components.message.controllers.DirectMessageControllerImpl
import com.components.message.controllers.RoomMessageControllerImpl
import com.components.message.repo.DirectMessageDAOImpl
import com.components.message.repo.RoomMessageDAOImpl
import com.components.user.controllers.UserControllerImpl
import com.components.user.repo.UserDAO
import com.components.user.repo.UserDAOImpl
import io.ktor.server.application.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun Application.configureDI() {
    DI{
        bindSingleton<UserDAO> { UserDAOImpl()}
        bindSingleton<ChatRoomDAO> { ChatRoomDAOImpl()}
        bindSingleton { DirectMessageDAOImpl()}
        bindSingleton { RoomMessageDAOImpl()}
        bindSingleton { UserControllerImpl(di) }
        bindSingleton { ChatRoomControllerImpl(di) }
        bindSingleton { DirectMessageControllerImpl(di) }
        bindSingleton { RoomMessageControllerImpl(di) }
    }
}
