package com.mtg.framework.dto.user

data class UserDto(
    val id: Int,
    val username: String
)

data class UserResponseDto(
    val access_token: String,
    val user: UserDto
)
