package com.mipt.kotlin.shuranda.api.model

import kotlinx.serialization.Serializable
@Serializable
data class AuthRequest(
    val login: String,
    val password: String
)
