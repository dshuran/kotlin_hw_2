package com.mipt.kotlin.shuranda.api

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mipt.kotlin.shuranda.model.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.Date

fun Application.routeLogin(secret: String, issuer: String, audience: String) {
    routing {
        post("/register") {
            val user: User = call.receive()
            val expireAfterAnHour = System.currentTimeMillis() + 3_600_000
            val profileCreatedDate = Date().toString()

            val generatedToken = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("userName", user.userName)
                .withClaim("password", user.password)
                .withClaim("userFirstName", user.userFirstName)
                .withClaim("profileCreatedDate", profileCreatedDate)
                .withExpiresAt(Date(expireAfterAnHour))
                .sign(Algorithm.HMAC512(secret))

            user.token = generatedToken
            user.profileCreatedDate = profileCreatedDate

            // TODO: save data to local DB

            call.respond(HttpStatusCode.OK, user)
        }

        authenticate {
            get ("/token") {
                val principal = call.principal<JWTPrincipal>()
                val userName = principal!!.payload.getClaim("userName").asString()
                val password = principal!!.payload.getClaim("password").asString()

                val user: User = User(userName = userName, password = password)
                call.respond(HttpStatusCode.OK, user)
            }
        }
    }
}