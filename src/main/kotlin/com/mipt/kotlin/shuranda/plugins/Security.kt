package com.mipt.kotlin.shuranda.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

fun Application.configureSecurity(secret: String, issuer: String, audience: String, myRealm: String) {
    install(Authentication) {
        jwt {
            realm = myRealm

            verifier(JWT.require(Algorithm.HMAC512(secret)).withAudience(audience).withIssuer(issuer).build())

            validate {
                jwtCredential -> kotlin.run {
                    val userName = jwtCredential.payload.getClaim("userName").asString()
                    if (userName.isNotEmpty()) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid!")
            }
        }
    }
}