package com.mtg.domain

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Int,
    val serviceName: String,
)
