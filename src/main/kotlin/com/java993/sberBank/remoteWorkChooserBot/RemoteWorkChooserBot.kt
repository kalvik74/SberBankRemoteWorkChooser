package com.java993.sberBank.remoteWorkChooserBot

import com.java993.sberBank.remoteWorkChooserBot.behaviour.NextBehaviour
import com.java993.sberBank.remoteWorkChooserBot.behaviour.UserAddBehaviour
import com.java993.sberBank.remoteWorkChooserBot.behaviour.UserDeleteBehaviour
import com.java993.sberBank.remoteWorkChooserBot.behaviour.UserListBehaviour
import org.artfable.telegram.api.LongPollingTelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RemoteWorkChooserBot @Autowired constructor(
        @Value("\${telegram.bot.token}") token: String,
        userListBehaviour: UserListBehaviour,
        userAddBehaviour: UserAddBehaviour,
        userDeleteBehaviour: UserDeleteBehaviour,
        nextBehaviour: NextBehaviour
)
    : LongPollingTelegramBot(token, setOf(userListBehaviour, userAddBehaviour, nextBehaviour), setOf(userDeleteBehaviour))