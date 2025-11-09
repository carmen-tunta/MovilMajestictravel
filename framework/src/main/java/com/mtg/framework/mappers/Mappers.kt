package com.mtg.framework.mappers

import com.mtg.domain.Bandeja
import com.mtg.domain.Service
import com.mtg.domain.User
import com.mtg.framework.dto.bandeja.BandejaResponseDto
import com.mtg.framework.dto.bandeja.ServiceResponseDto
import com.mtg.framework.dto.user.UserResponseDto

fun UserResponseDto.toModel(): User {
    return User(
        username = user.username,
        accessToken = access_token,
        id = user.id,
    )
}

fun ServiceResponseDto.toModel(): Service {
    return Service(
        id = service.id,
        serviceName = service.name,
    )
}

fun BandejaResponseDto.toModel(): Bandeja {
    return Bandeja(
        id = id,
        passengerName = passengerName,
        countryCode = countryCode,
        whatsapp = whatsapp,
        services = services.map { it.toModel() },
        travelDate = travelDate,
        createdAt = createdAt,
        assignedAt = assignedAt,
        status = status,
    )
}
