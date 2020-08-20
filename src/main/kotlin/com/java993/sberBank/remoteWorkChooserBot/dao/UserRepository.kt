package com.java993.sberBank.remoteWorkChooserBot.dao

import com.java993.sberBank.remoteWorkChooserBot.dao.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long> {
}