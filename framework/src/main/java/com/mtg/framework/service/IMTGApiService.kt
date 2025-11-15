package com.mtg.framework.service

import com.mtg.framework.dto.bandeja.BandejaResponseDto
import com.mtg.framework.dto.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class LoginRequest(
    val username: String,
    val password: String
)

data class TakeRequestBody(
    val agentId: String
)

data class AtenderRequestBody(
    val status: String = "en_progreso"
)

data class RegisterFCMTokenRequest(
    val token: String,
    val platform: String = "android"
)

data class RegisterFCMTokenResponse(
    val message: String
)

interface IMTGApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<UserResponseDto>

    @GET("quote-requests/by-agent/{agentId}")
    suspend fun getByAgent(
        @Path("agentId") agentId: String,
        @Header("Authorization") token: String,
    ): Response<List<BandejaResponseDto>>

    @PATCH("quote-requests/{id}/take")
    suspend fun takeRequest(
        @Path("id") id: String,
        @Body body: TakeRequestBody,
        @Header("Authorization") token: String,
    ): Response<BandejaResponseDto>

    @PATCH("quote-requests/{id}/release")
    suspend fun releaseRequest(
        @Path("id") id: String,
        @Body body: TakeRequestBody,
        @Header("Authorization") token: String,
    ): Response<BandejaResponseDto>

    @PATCH("quote-requests/{id}/cotizando")
    suspend fun cotizandoRequest(
        @Path("id") id: String,
        @Body body: TakeRequestBody,
        @Header("Authorization") token: String,
    ): Response<BandejaResponseDto>

    @PATCH("quote-requests/{id}/sin-respuesta")
    suspend fun requestSinRespuesta(
        @Path("id") id: String,
        @Body body: TakeRequestBody,
        @Header("Authorization") token: String,
    ): Response<BandejaResponseDto>

    @PUT("quote-requests/{id}")
    suspend fun atenderRequest(
        @Path("id") id: String,
        @Body body: AtenderRequestBody,
        @Header("Authorization") token: String,
    ): Response<BandejaResponseDto>

    @POST("notifications/register-token")
    suspend fun registerFCMToken(
        @Body request: RegisterFCMTokenRequest,
        @Header("Authorization") token: String,
    ): Response<RegisterFCMTokenResponse>
}
