package com.java993.sberBank.remoteWorkChooserBot.service

import com.java993.sberBank.remoteWorkChooserBot.model.User

interface UserService {
    fun getAll(): List<User>
    fun create(user: User)
    fun remove(id: Long)
    fun nextRemoteWorkers(count: Int): List<User>
}
