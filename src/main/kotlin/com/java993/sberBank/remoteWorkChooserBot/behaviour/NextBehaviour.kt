package com.java993.sberBank.remoteWorkChooserBot.behaviour

import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NextBehaviour : Behaviour {

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
                                chatId = message.chat.id.toString(),
                                text = "Кручу-верчу обмануть хочу........"

                        )
                )
                Thread.sleep(2000)
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id.toString(),
                                text = userService.nextRemoteWorkers(1).map { user -> "${user.name}" }.joinToString(
                                        prefix = "Наши победители : \n\n",
                                        separator = "\n"
                                )

                        )
                )
            }
            update?.message?.text?.startsWith("/reset") == true -> {
                val message = update.extractMessage()!!
                userService.resetAllResults()
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id.toString(),
                                text = "all results were reset"
                        )
                )
            }
        }
    }
}