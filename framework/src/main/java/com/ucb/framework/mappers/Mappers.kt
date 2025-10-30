package com.ucb.framework.mappers

import com.ucb.domain.Bandeja
import com.ucb.domain.Gitalias
import com.ucb.domain.Movie
import com.ucb.domain.Service
import com.ucb.domain.User
import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.MovieDto
import com.ucb.framework.dto.bandeja.BandejaResponseDto
import com.ucb.framework.dto.bandeja.ServiceDto
import com.ucb.framework.dto.bandeja.ServiceResponseDto
import com.ucb.framework.dto.user.UserResponseDto
import com.ucb.framework.persistence.GitAccount

fun AvatarResponseDto.toModel(): Gitalias {
    return Gitalias(
        login = login,
        avatarUrl = url
    )
}

fun Gitalias.toEntity(): GitAccount {
    return GitAccount(login)
}

fun GitAccount.toModel(): Gitalias {
    return Gitalias(
        alias,
        ""
    )
}

fun UserResponseDto.toModel(): User {
    return User(
        username = user.username,
        accessToken = access_token,
        id = user.id,
    )
}

fun ServiceResponseDto.toModel(): Service {
    return Service(
        id = id,
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
    )
}

fun MovieDto.toModel(): Movie {
    return Movie(
        title = title,
        overview = overview,
        posterPath = posterPath,
        id = "1"
    )
}