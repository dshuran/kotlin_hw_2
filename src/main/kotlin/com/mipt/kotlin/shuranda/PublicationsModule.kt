package com.mipt.kotlin.shuranda

import com.mipt.kotlin.shuranda.repository.PublicationsRepository
import com.mipt.kotlin.shuranda.repository.impl.DefaultPublicationsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val publicationsModule = module {
    singleOf(::DefaultPublicationsRepository) bind PublicationsRepository::class
}