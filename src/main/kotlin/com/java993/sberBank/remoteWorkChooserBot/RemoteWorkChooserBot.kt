package com.java993.sberBank.remoteWorkChooserBot

import com.java993.sberBank.remoteWorkChooserBot.behaviour.UserDeleteBehaviour
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.LongPollingTelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RemoteWorkChooserBot @Autowired constructor(
        userListBehaviour: Behaviour,
        userAddBehaviour: Behaviour,
        userDeleteBehaviour: UserDeleteBehaviour,
        nextBehaviour: Behaviour
)
    : LongPollingTelegramBot(mutableSetOf(userListBehaviour, userAddBehaviour, nextBehaviour), setOf(userDeleteBehaviour))