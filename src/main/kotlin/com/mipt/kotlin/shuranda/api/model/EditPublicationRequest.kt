package com.mipt.kotlin.shuranda.api.model

import kotlinx.serialization.Serializable

@Serializable
data class EditPublicationRequest(
    val newPublicationText: String
)
