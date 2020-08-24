package com.java993.sberBank.remoteWorkChooserBot.behaviour

import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.artfable.telegram.api.AbstractBehaviour
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.keyboard.InlineKeyboard
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserListBehaviour : AbstractBehaviour(true) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserListBehaviour::class.java)
    }

    @Autowired
    private lateinit var userService: UserService;

    @Autowired
    private lateinit var userDeleteBehaviour: UserDeleteBehaviour

    @Autowired
    private lateinit var telegramSender: TelegramSender


    override fun start() {
    }

    override fun parse(update: Update) {
        when {
            update.message?.text?.startsWith("/users") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id,
                                text = userService.getAll().map { user -> "${user.name}" }.joinToString(
                                        prefix = "*Users:* \n",
                                        separator = "\n",
                                        postfix = "\n\nalso available /delete and /add commands"
                                ),
                                parseMode = ParseMode.MARKDOWN_V2,

                                )
                )
            }
            update.message?.text?.startsWith("/delete") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id,
                                text = "Choose User to delete \n\n",
                                parseMode = ParseMode.MARKDOWN_V2,
                                replyMarkup = InlineKeyboard(
                                        *(userService.getAll()
                                                .toList()
                                                .map {
                                                    userDeleteBehaviour.createBtn(it.name, it.id.toString())
                                                }
                                                .toTypedArray())
                                )

                        )
                )
            }
        }
    }

}