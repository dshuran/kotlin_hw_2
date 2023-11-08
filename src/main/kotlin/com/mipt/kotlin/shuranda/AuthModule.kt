package com.mipt.kotlin.shuranda

import com.mipt.kotlin.shuranda.repository.UsersRepository
import com.mipt.kotlin.shuranda.repository.impl.DefaultUsersRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::DefaultUsersRepository) bind UsersRepository::class
}