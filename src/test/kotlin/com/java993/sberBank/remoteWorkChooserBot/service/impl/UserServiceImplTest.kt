package com.java993.sberBank.remoteWorkChooserBot.service.impl

import com.java993.sberBank.remoteWorkChooserBot.dao.entity.UserEntity
import com.java993.sberBank.remoteWorkChooserBot.model.User
import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalStateException

@SpringBootTest
internal class UserServiceImplTest @Autowired constructor(
        val userService: UserService) {


    @AfterEach
    internal fun tearDown() {
        userService.getAll().forEach { (userService.remove(it.id)) }
    }

    @Test
    fun `When nextRemoteWorkers runs 4 times with 20 users then return 20 users`() {
        for(i in 1..20) userService.create(User(name = "User_${i}"))

        val users = mutableListOf<User>()
        for (i in 1..4) users += userService.nextRemoteWorkers(5)
        Assertions.assertThat(users.size).isEqualTo(20)
    }

    @Test
    fun `When nextRemoteWorkers runs 2 times with 20 users then return 10 users`() {
        for(i in 1..20) userService.create(User(name = "User_${i}"))

        val userSet = mutableSetOf<User>()
        for (i in 1..2) userSet += userService.nextRemoteWorkers(5)

        Assertions.assertThat(userSet.size).isEqualTo(10)
    }

    @Test
    fun `When nextRemoteWorkers runs 5 times with 3 users then return 3 users`() {
        for(i in 1..3) userService.create(User(name = "User_${i}"))

        val userSet = mutableSetOf<User>()
        for (i in 1..5) userSet += userService.nextRemoteWorkers(5)

        Assertions.assertThat(userSet.size).isEqualTo(3)
    }

    @Test
    fun `When nextRemoteWorkers runs 5 times with 5 users then return 5 users`() {
        for(i in 1..5) userService.create(User(name = "User_${i}"))

        val userSet = mutableSetOf<User>()
        for (i in 1..5) userSet += userService.nextRemoteWorkers(5)

        Assertions.assertThat(userSet.size).isEqualTo(5)
    }


    @Test
    fun `When create 2 users with same names then return exception`() {
        userService.create(User(name = "User"))
        assertThrows(IllegalStateException::class.java){
            userService.create(User(name = "User"))
        }
    }

    @Test
    fun `When create 2 users with same names but different register then return exception`() {
        userService.create(User(name = "User"))
        assertThrows(IllegalStateException::class.java){
            userService.create(User(name = "user"))
        }
    }

    @Test
    fun `When getAll then return 2 users`() {
        Assertions.assertThat(userService.getAll().size).isEqualTo(0)

        userService.create(User(name = "John1"))
        userService.create(User(name = "John2"))

        Assertions.assertThat(userService.getAll().size).isEqualTo(2)
    }

    @Test
    fun `When getById then return user`() {
        Assertions.assertThat(userService.getAll().size).isEqualTo(0)

        val user1 = userService.create(User(name = "John1"))
        userService.create(User(name = "John2"))

        Assertions.assertThat(userService.getById(user1.id).get()).isEqualTo(user1)
    }
}