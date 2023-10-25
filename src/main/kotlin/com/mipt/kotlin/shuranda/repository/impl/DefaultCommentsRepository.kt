package com.mipt.kotlin.shuranda.repository.impl

import com.mipt.kotlin.shuranda.model.Comment
import com.mipt.kotlin.shuranda.repository.CommentsRepository
import java.time.Instant
import kotlin.random.Random


class DefaultCommentsRepository : CommentsRepository {

    private val comments: MutableSet<Comment> = mutableSetOf()
    override fun getAll(): Collection<Comment> {
        return comments.toList()
    }

    override fun getById(id: Long): Comment? {
        return comments.find { it.id == id }
    }

    override fun createComment(commentText: String): Comment {
        val createdComment = Comment(
            id = Random.nextLong(1, Long.MAX_VALUE),
            text = commentText,
            createdAt = Instant.now().toString()
        )

        comments.add(createdComment)

        return createdComment
    }
}