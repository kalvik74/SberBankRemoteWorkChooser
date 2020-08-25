package com.java993.sberBank.remoteWorkChooserBot.service.impl

import com.java993.sberBank.remoteWorkChooserBot.model.User
import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UserServiceImplTest @Autowired constructor(
        val userService: UserService) {


    @AfterEach
    internal fun tearDown() {
        userService.getAll().forEach { (userService.remove(it.id)) }
    }

    @Test
    fun nextRemoteWorkers_20_users_4_runs() {
        for(i in 1..20) userService.create(User(name = "User_${i}"))

        val users = mutableListOf<User>()
        for (i in 1..4) users += userService.nextRemoteWorkers(5)
        Assertions.assertThat(users.size).isEqualTo(20)
    }

    @Test
    fun nextRemoteWorkers_20_users_2_runs() {
        for(i in 1..20) userService.create(User(name = "User_${i}"))

        val userSet = mutableSetOf<User>()
        for (i in 1..2) userSet += userService.nextRemoteWorkers(5)

        Assertions.assertThat(userSet.size).isEqualTo(10)
    }

    @Test
    fun nextRemoteWorkers_3_users_5_runs() {
        for(i in 1..3) userService.create(User(name = "User_${i}"))

        val userSet = mutableSetOf<User>()
        for (i in 1..5) userSet += userService.nextRemoteWorkers(5)

        Assertions.assertThat(userSet.size).isEqualTo(3)
    }

    @Test
    fun nextRemoteWorkers_5_users_5_runs() {
        for(i in 1..5) userService.create(User(name = "User_${i}"))

        val userSet = mutableSetOf<User>()
        for (i in 1..5) userSet += userService.nextRemoteWorkers(5)

        Assertions.assertThat(userSet.size).isEqualTo(5)
    }
}