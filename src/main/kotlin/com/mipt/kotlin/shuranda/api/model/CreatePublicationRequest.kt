package com.mipt.kotlin.shuranda.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePublicationRequest(
    val publicationText: String
)
