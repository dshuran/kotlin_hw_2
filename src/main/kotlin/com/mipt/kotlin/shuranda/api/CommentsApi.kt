package com.mipt.kotlin.shuranda.api

import com.mipt.kotlin.shuranda.api.model.CreateCommentRequest
import com.mipt.kotlin.shuranda.repository.CommentsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.lang.Exception

fun Application.commentsApi() {
    routing {

        val commentsRepository by inject<CommentsRepository>()

        get("/comments/all") {
            try {
                val comments = commentsRepository.getAll()
                call.respond(comments)
            } catch (ex: Exception) {
                println(ex.message)
            }
        }

        get("/comment/{id}/get") {
            val commentId = call.parameters["id"]?.toLong() ?: 0

            val comment = commentsRepository.getById(commentId)

            if (comment == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(comment)
            }
        }

        put("/comment/create") {
            val request = call.receive<CreateCommentRequest>()

            val createdComment = commentsRepository.createComment(request.commentText)

            call.respond(createdComment)
        }

    }
}