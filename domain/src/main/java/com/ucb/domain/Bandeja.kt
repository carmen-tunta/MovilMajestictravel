package com.ucb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Bandeja(
    val id: Int,
    val passengerName: String,
    val countryCode: String,
    val whatsapp: String,
    val services: List<Service>,
    val travelDate: String?,
    val createdAt: String,
)
