package com.mipt.kotlin.shuranda.repository

import com.mipt.kotlin.shuranda.model.User

interface UsersRepository {

    fun getByUsername(username: String): User?
    
    fun saveByUsername(user: User)
}