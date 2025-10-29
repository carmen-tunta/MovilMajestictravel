package com.ucb.framework.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class UserDto(
    val id: Int,
    val username: String
)

data class UserResponseDto(
    val access_token: String,
    val user: UserDto
)
