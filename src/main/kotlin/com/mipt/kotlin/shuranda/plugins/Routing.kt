package com.mipt.kotlin.shuranda.plugins

import com.mipt.kotlin.shuranda.api.publicationsApi
import com.mipt.kotlin.shuranda.api.routeLogin
import io.ktor.server.application.Application

fun Application.configureRouting(secret: String, issuer: String, audience: String) {
    routeLogin(secret=secret, issuer=issuer, audience=audience)
    publicationsApi()
}