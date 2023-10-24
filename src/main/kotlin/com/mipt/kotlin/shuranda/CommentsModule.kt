package com.mipt.kotlin.shuranda

import com.mipt.kotlin.shuranda.repository.CommentsRepository
import com.mipt.kotlin.shuranda.repository.impl.DefaultCommentsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commentsModule = module {
    singleOf(::DefaultCommentsRepository) bind CommentsRepository::class
}