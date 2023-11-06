package com.mipt.kotlin.shuranda.model

import kotlinx.serialization.Serializable

@Serializable
data class Publication(
    val id: Long,
    var text: String,
    val createdAt: String,
    var editedAt: String
)
