package com.java993.sberBank.remoteWorkChooserBot.behaviour

import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.artfable.telegram.api.AbstractBehaviour
import org.artfable.telegram.api.Message
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserBehaviour: AbstractBehaviour(true) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserBehaviour::class.java)
    }

    @Autowired
    private lateinit var userService: UserService;

    override fun start() {
        //TODO("Not yet implemented")
    }

    override fun parse(updates: MutableList<Update>?) {
        updates?.forEach(this::parse)
    }

    private fun parse(update: Update) {
        logger.trace(update.toString())
        when {
            update.message?.text?.startsWith("/users") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod<Message>(
                        SendMessageRequest(
                                message.chat.id,
                                userService.getAll().joinToString(
                                        prefix = "Users: \n",
                                        separator = "\n",
                                        postfix = "\nalso available /delete and /add commands"
                                )
                        )
                )
            }
        }
    }

}