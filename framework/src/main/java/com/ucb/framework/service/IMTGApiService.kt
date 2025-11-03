package com.ucb.framework.service

import com.ucb.domain.Bandeja
import com.ucb.framework.dto.MovieResponseDto
import com.ucb.framework.dto.bandeja.BandejaResponseDto
import com.ucb.framework.dto.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class LoginRequest(
    val username: String,
    val password: String
)

data class TakeRequestBody(
    val agentId: String
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
    ) : Response<List<BandejaResponseDto>>

    @PATCH("quote-requests/{id}/take")
    suspend fun takeRequest(
        @Path("id") id: String,
        @Body body: TakeRequestBody,
        @Header("Authorization") token: String,
    ) : Response<BandejaResponseDto>
}
