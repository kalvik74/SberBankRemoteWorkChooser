package com.java993.sberBank.remoteWorkChooserBot.dao

import com.java993.sberBank.remoteWorkChooserBot.dao.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository) {

    @Test
    fun `When findById then return UserEntity`() {
        val user = UserEntity(name = "John", remoteWorkCount = 0);
        entityManager.persist(user)
        entityManager.flush()

        val found = userRepository.findById(user.id!!)
        assertThat(found.get()).isEqualTo(user)
    }

    @Test
    fun `When findById then return Null`() {
        val found = userRepository.findById(2)
        assertThat(found.isEmpty).isTrue()
    }


    @Test
    fun `When findAll then return 2 users`() {
        val user1 = UserEntity(name = "John", remoteWorkCount = 0);
        entityManager.persist(user1)
        val user2 = UserEntity(name = "John", remoteWorkCount = 0);
        entityManager.persist(user2)
        entityManager.flush()

        val found = userRepository.findAll()
        assertThat(found.count()).isEqualTo(2)
    }



}