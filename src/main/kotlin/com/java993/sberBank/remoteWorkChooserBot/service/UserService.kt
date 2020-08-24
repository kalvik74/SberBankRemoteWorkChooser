package com.java993.sberBank.remoteWorkChooserBot.service

import com.java993.sberBank.remoteWorkChooserBot.dao.entity.UserEntity
import com.java993.sberBank.remoteWorkChooserBot.model.User
import java.util.*

interface UserService {
    fun getAll(): List<User>
    fun getById(id: Long): Optional<UserEntity>
    fun create(user: User)
    fun remove(id: Long)
    fun nextRemoteWorkers(count: Int): List<User>
}
