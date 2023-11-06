package com.mipt.kotlin.shuranda.repository

import com.mipt.kotlin.shuranda.model.Publication

interface PublicationsRepository {

    fun getAllForPage(pageId: Long): Collection<Publication>

    fun getById(id: Long): Publication?

    fun createPublication(publicationText: String): Publication
}