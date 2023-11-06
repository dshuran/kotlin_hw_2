package com.mipt.kotlin.shuranda.model

import kotlinx.serialization.Serializable

@Serializable
data class User(

    val password: String? = null,

    val userName: String? = null,

    var token: String? = null


)
