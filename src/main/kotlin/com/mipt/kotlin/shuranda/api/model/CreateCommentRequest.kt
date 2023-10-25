package com.mipt.kotlin.shuranda.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentRequest(
    val commentText: String
)
