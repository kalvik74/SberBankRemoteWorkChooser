package com.java993.sberBank.remoteWorkChooserBot.model

data class User(
        val id: Long = 0,
        val name: String,
        val alreadyWasChosen: Boolean = false
)