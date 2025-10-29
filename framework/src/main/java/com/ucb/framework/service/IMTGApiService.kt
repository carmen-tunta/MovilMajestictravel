package com.ucb.framework.service

import com.ucb.framework.dto.MovieResponseDto
import com.ucb.framework.dto.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginRequest(
    val username: String,
    val password: String
)

interface IMTGApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<UserResponseDto>
}
