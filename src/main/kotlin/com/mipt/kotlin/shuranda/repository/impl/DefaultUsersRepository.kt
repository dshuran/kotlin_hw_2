package com.mipt.kotlin.shuranda.repository.impl

import com.mipt.kotlin.shuranda.model.User
import com.mipt.kotlin.shuranda.repository.UsersRepository

class DefaultUsersRepository : UsersRepository {

    private val users: MutableMap<String, User> = mutableMapOf()
    override fun getByUsername(username: String): User? {
        return users[username]
    }

    override fun saveByUsername(user: User) {
        if (user.userName is String) {
            users[user.userName] = user
        }
    }
}