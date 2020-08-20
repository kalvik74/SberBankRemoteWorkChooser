package com.java993.sberBank.remoteWorkChooserBot.model

data class User(
        val id: Long,
        val name: String,
        val remoteWorkCount: Long = 0
)