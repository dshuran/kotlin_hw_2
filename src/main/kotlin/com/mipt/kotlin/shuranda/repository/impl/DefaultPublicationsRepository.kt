package com.mipt.kotlin.shuranda.repository.impl

import com.mipt.kotlin.shuranda.model.Publication
import com.mipt.kotlin.shuranda.repository.PublicationsRepository
import java.time.Instant
import kotlin.math.min
import kotlin.random.Random

class DefaultPublicationsRepository : PublicationsRepository {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

    private val publications: MutableSet<Publication> = mutableSetOf()
    override fun getAllForPage(pageId: Long): Collection<Publication> {

        val startIndex = (pageId.toInt() - 1) * DEFAULT_PAGE_SIZE
        if (startIndex >= publications.size) {
            return emptyList()
        }
        val endIndex = min(pageId.toInt() * DEFAULT_PAGE_SIZE, publications.size)

        return publications.toList().subList(
            startIndex,
            endIndex
        )
    }

    override fun getById(id: Long): Publication? {
        return publications.find { it.id == id }
    }

    override fun createPublication(publicationText: String): Publication {
        val createdPublication = Publication(
            id = Random.nextLong(1, Long.MAX_VALUE),
            text = publicationText,
            createdAt = Instant.now().toString(),
            editedAt = ""
        )

        publications.add(createdPublication)

        return createdPublication
    }
}