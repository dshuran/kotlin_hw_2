package com.mipt.kotlin.shuranda

import com.mipt.kotlin.shuranda.api.publicationsApi
import com.mipt.kotlin.shuranda.plugins.configureRouting
import com.mipt.kotlin.shuranda.plugins.configureSecurity
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val secret = config.property("jwt.secret").getString()
    val issuer = config.property("jwt.issuer").getString()
    val audience = config.property("jwt.audience").getString()
    val realm = config.property("jwt.realm").getString()
    embeddedServer(Netty, port = 8080) {
        configureSecurity(secret=secret, issuer=issuer, audience=audience, myRealm=realm)
        configureServer()
        configureRouting(secret=secret, issuer=issuer, audience=audience)
    }.start(wait = true)
}

fun Application.configureServer() {
    install(Koin) {
        modules(publicationsModule)
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
}