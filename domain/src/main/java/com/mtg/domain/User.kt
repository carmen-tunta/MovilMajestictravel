package com.mtg.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val accessToken: String,
    val id: Int,
)
