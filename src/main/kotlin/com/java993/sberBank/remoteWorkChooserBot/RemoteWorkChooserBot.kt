package com.java993.sberBank.remoteWorkChooserBot

import com.java993.sberBank.remoteWorkChooserBot.behaviour.UserBehaviour
import org.artfable.telegram.api.LongPollingTelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RemoteWorkChooserBot @Autowired constructor(
        @Value("\${telegram.bot.token}") token: String,
        userBehaviour: UserBehaviour
)
    : LongPollingTelegramBot(token, setOf(userBehaviour), setOf())