package com.java993.sberBank.remoteWorkChooserBot.behaviour

import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.artfable.telegram.api.AbstractCallbackBehaviour
import org.artfable.telegram.api.CallbackQuery
import org.artfable.telegram.api.request.DeleteMessageRequest
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserDeleteBehaviour : AbstractCallbackBehaviour("deleteUser") {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserDeleteBehaviour::class.java)
    }

    @Autowired
    private lateinit var telegramSender: TelegramSender

    @Autowired
    private lateinit var userService: UserService;

    override fun parse(value: String?, callbackQuery: CallbackQuery?) {
        value?.let { userService.remove(it.toLong()) }


        telegramSender.executeMethod(
                DeleteMessageRequest(
                        chatId = callbackQuery?.message?.chat?.id!!,
                        messageId = callbackQuery.message?.messageId!!

                )
        )
        telegramSender.executeMethod(
                SendMessageRequest(
                        chatId = callbackQuery.message?.chat?.id!!,
                        text = "user with id=${value} was deleted"
                )
        )
    }

}