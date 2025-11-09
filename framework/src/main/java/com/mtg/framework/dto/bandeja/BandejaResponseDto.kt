package com.mtg.framework.dto.bandeja

data class ServiceDto(
    val id: Int,
    val name: String,
)

data class ServiceResponseDto(
    val id: Int,
    val service: ServiceDto,
)

data class BandejaResponseDto(
    val id: Int,
    val passengerName: String,
    val countryCode: String,
    val whatsapp: String,
    val services: List<ServiceResponseDto>,
    val travelDate: String?,
    val createdAt: String,
    val assignedAt: String,
    val status: String,
)