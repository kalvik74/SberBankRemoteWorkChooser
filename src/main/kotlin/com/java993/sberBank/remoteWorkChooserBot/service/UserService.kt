package com.java993.sberBank.remoteWorkChooserBot.service

import com.java993.sberBank.remoteWorkChooserBot.model.User

interface UserService {
    fun getAll(): List<User>;
}
