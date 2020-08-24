package com.java993.sberBank.remoteWorkChooserBot.service.impl

import com.java993.sberBank.remoteWorkChooserBot.dao.UserRepository
import com.java993.sberBank.remoteWorkChooserBot.dao.entity.UserEntity
import com.java993.sberBank.remoteWorkChooserBot.model.User
import com.java993.sberBank.remoteWorkChooserBot.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random.Default.nextInt

@Service
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userRepository: UserRepository;

    override fun getAll(): List<User> {
        return userRepository
                .findByOrderByNameAsc()
                .map { User(it) }
                .toList()
    }

    override fun getById(id: Long): Optional<UserEntity> = userRepository.findById(id)

    override fun create(user: User) {
        userRepository.save(UserEntity(name = user.name, alreadyWasChosen = user.alreadyWasChosen))
    }

    override fun remove(id: Long) {
        userRepository.deleteById(id)
    }

    override fun nextRemoteWorkers(count: Int): List<User> {
        return when {
            userRepository.count() <= count -> userRepository.findAll().map { User(it) }.toList()
            else -> {

                val lastNotChosenUsers = when {
                    userRepository.countByAlreadyWasChosen(false) <= count ->
                        userRepository.findByAlreadyWasChosen(false).toMutableList()
                    else -> mutableListOf()
                }

                val newCandidates = when {
                    lastNotChosenUsers.size > 0 -> {
                        userRepository.findByAlreadyWasChosen(true).map {
                            userRepository.save(UserEntity(
                                    id = it.id,
                                    name = it.name,
                                    alreadyWasChosen = false
                            ))
                            it
                        }.toMutableList()
                    }
                    else -> userRepository.findByAlreadyWasChosen(false).toMutableList()
                }

                for (i in 1..(count - lastNotChosenUsers.size)) {
                    val randomIndex: Int = nextInt(0, newCandidates.size - 1)
                    lastNotChosenUsers += newCandidates.removeAt(randomIndex);
                }
                lastNotChosenUsers.map {
                    User(userRepository.save(UserEntity(
                            id = it.id,
                            name = it.name,
                            alreadyWasChosen = true
                    )))
                }
            }
        }
    }

    fun User(entity: UserEntity): User = User(
            id = entity.id,
            name = entity.name,
            alreadyWasChosen = entity.alreadyWasChosen
    )

}