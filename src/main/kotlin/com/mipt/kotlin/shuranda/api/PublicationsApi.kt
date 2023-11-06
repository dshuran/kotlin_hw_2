package com.mipt.kotlin.shuranda.api

import com.mipt.kotlin.shuranda.api.model.CreatePublicationRequest
import com.mipt.kotlin.shuranda.api.model.EditPublicationRequest
import com.mipt.kotlin.shuranda.repository.PublicationsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.publicationsApi() {
    routing {

        val publicationsRepository by inject<PublicationsRepository>()

        get("/publications/all/{id}") {
            val publicationId = parsePublicationId(call)
            val comments = publicationsRepository.getAllForPage(publicationId)
            call.respond(comments)
        }

        get("/publication/{id}/get") {
            val publicationId = parsePublicationId(call)

            val publication = publicationsRepository.getById(publicationId)

            if (publication == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(publication)
            }
        }

        patch("/publication/{id}/edit") {
            val publicationId = parsePublicationId(call)
            val editRequest = call.receive<EditPublicationRequest>()

            val publication = publicationsRepository.getById(publicationId)

            if (publication == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                publication.text = editRequest.newPublicationText
                publication.editedAt = Instant.now().toString()
                call.respond(publication)
            }
        }

        put("/publication/create") {
            val request = call.receive<CreatePublicationRequest>()

            val createdComment = publicationsRepository.createPublication(request.publicationText)

            call.respond(createdComment)
        }

    }
}

fun ApplicationCall.getPathParameter(name: String): String? {
    return parameters[name]
}

fun parsePublicationId(call: ApplicationCall): Long {
    return call.getPathParameter("id")?.toLong() ?: 0
}