package com.java993.sberBank.remoteWorkChooserBot.dao.entity

import javax.persistence.*

@Entity
data class UserEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String,
        val alreadyWasChosen: Boolean = false
)