package com.mipt.kotlin.shuranda.api

import com.mipt.kotlin.shuranda.api.model.CreatePublicationRequest
import com.mipt.kotlin.shuranda.api.model.EditPublicationRequest
import com.mipt.kotlin.shuranda.repository.PublicationsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.publicationsApi() {
    routing {

        val publicationsRepository by inject<PublicationsRepository>()

        authenticate {
            patch("/publication/{id}/edit") {
                val principal = call.principal<JWTPrincipal>()
                val userName = principal!!.payload.getClaim("userName").asString()

                val publicationId = parsePublicationId(call)
                val editRequest = call.receive<EditPublicationRequest>()

                val publication = publicationsRepository.getById(publicationId)

                if (publication == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    if (publication.creatorUserName != userName) {
                        call.respond(HttpStatusCode.Forbidden)
                    } else {
                        publication.text = editRequest.newPublicationText
                        publication.editedAt = Instant.now().toString()
                        call.respond(publication)
                    }
                }
            }

            put("/publication/create") {
                val request = call.receive<CreatePublicationRequest>()

                val principal = call.principal<JWTPrincipal>()
                val userName = principal!!.payload.getClaim("userName").asString()

                val createdPublication = publicationsRepository.createPublication(request.publicationText, userName)

                call.respond(createdPublication)
            }

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
        }

    }
}

fun ApplicationCall.getPathParameter(name: String): String? {
    return parameters[name]
}

fun parsePublicationId(call: ApplicationCall): Long {
    return call.getPathParameter("id")?.toLong() ?: 0
}