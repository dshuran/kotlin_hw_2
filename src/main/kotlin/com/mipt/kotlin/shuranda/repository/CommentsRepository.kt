package com.mipt.kotlin.shuranda.repository

import com.mipt.kotlin.shuranda.model.Comment

interface CommentsRepository {

    fun getAll(): Collection<Comment>

    fun getById(id: Long): Comment?

    fun createComment(commentText: String): Comment
}