package com.ucb.framework.mappers

import com.ucb.domain.Bandeja
import com.ucb.domain.Service
import com.ucb.domain.User
import com.ucb.framework.dto.bandeja.BandejaResponseDto
import com.ucb.framework.dto.bandeja.ServiceResponseDto
import com.ucb.framework.dto.user.UserResponseDto

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
