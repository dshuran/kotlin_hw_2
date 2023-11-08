package com.mipt.kotlin.shuranda.api

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mipt.kotlin.shuranda.api.model.AuthRequest
import com.mipt.kotlin.shuranda.model.User
import com.mipt.kotlin.shuranda.repository.UsersRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import java.util.Date

fun Application.routeLogin(secret: String, issuer: String, audience: String) {

    val usersRepository by inject<UsersRepository>()

    routing {
        post("/register") {
            val authRequest: AuthRequest = call.receive()
            val expireAfterAnHour = System.currentTimeMillis() + 3_600_000
            val profileCreatedDate = Date().toString()

            val user = User(userName = authRequest.login, password = authRequest.password)

            val generatedToken = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("userName", user.userName)
                .withClaim("password", user.password)
                .withExpiresAt(Date(expireAfterAnHour))
                .sign(Algorithm.HMAC512(secret))

            user.token = generatedToken
            user.profileCreatedDate = profileCreatedDate

            usersRepository.saveByUsername(user)

            call.respond(HttpStatusCode.OK, user)
        }

        post ("/login") {
            val authRequest: AuthRequest = call.receive()
            val expireAfterAnHour = System.currentTimeMillis() + 3_600_000

            val user = usersRepository.getByUsername(authRequest.login)
            if (
                user != null &&
                (authRequest.login == user.userName && authRequest.password == user.password)
            ) {
                val generatedToken = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("userName", user.userName)
                    .withClaim("password", user.password)
                    .withExpiresAt(Date(expireAfterAnHour))
                    .sign(Algorithm.HMAC512(secret))

                user.token = generatedToken
                usersRepository.saveByUsername(user)

                call.respond(user)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}