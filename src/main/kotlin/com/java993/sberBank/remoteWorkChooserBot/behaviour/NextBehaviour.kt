package com.java993.sberBank.remoteWorkChooserBot.behaviour

import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.artfable.telegram.api.AbstractBehaviour
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NextBehaviour : AbstractBehaviour(true) {

    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Autowired
    private lateinit var userService: UserService;

    override fun parse(update: Update?) {
        when {
            update?.message?.text?.startsWith("/next") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id,
                                text = userService.nextRemoteWorkers(5).map { user -> "${user.name}" }.joinToString(
                                        prefix = "Our winners : \n\n",
                                        separator = "\n"
                                )

                        )
                )
            }
        }
    }
}