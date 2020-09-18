package com.java993.sberBank.remoteWorkChooserBot.behaviour

import com.java993.sberBank.remoteWorkChooserBot.model.User
import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.Message
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserAddBehaviour : Behaviour {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserDeleteBehaviour::class.java)
    }

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var telegramSender: TelegramSender

    //TODO make tread-safe it
    val addState = mutableSetOf<Long>()


    override fun parse(update: Update) {
        logger.info("update received ${update}")
        when {

            //start adding process
            update.message?.text?.startsWith("/add") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod<Message>(
                        SendMessageRequest(
                                chatId = message.chat.id.toString(),
                                text = "Enter new user name",
                                parseMode = ParseMode.MARKDOWN_V2
                        )
                )
                message.from?.id?.let { addState.add(it) }
            }

            //delete remove state if user choose different command
            update.message?.text?.startsWith("/")!! -> {
                addState.remove(update.message?.from?.id);
            }

            //end adding process
            addState.contains(update.message?.from?.id)  -> {
                val message = update.extractMessage()!!
                addState.remove(message.from?.id);
                val userAndWorksCount = message.text?.split(";")?.map { it.trim() }?.toList()
                message.text?.let {
                    User(name = it)
                }?.let {
                    telegramSender.executeMethod<Message>(
                            SendMessageRequest(
                                    chatId = message.chat.id.toString(),
                                    text = try {
                                        userService.create(it)
                                        "user \"${userAndWorksCount?.get(0)}\" added"
                                    } catch (e: Exception){
                                        e.message ?: "Error creating user"
                                    }
                            )
                    )
                }
            }

        }

    }

}