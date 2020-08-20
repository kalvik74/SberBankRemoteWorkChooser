package com.java993.sberBank.remoteWorkChooserBot.service.impl

import com.java993.sberBank.remoteWorkChooserBot.dao.UserRepository
import com.java993.sberBank.remoteWorkChooserBot.model.User
import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userRepository: UserRepository;

    override fun getAll(): List<User> {
        return userRepository
                .findAll()
                .map { u ->
                    User(
                            id = u.id,
                            name = u.name,
                            remoteWorkCount = u.remoteWorkCount
                    )
                }
                .toList()
    }
}