package com.mtg.framework.user

import com.mtg.data.NetworkResult
import com.mtg.data.user.IUserRemoteDataSource
import com.mtg.domain.User
import com.mtg.framework.mappers.toModel
import com.mtg.framework.service.LoginRequest
import com.mtg.framework.service.RetrofitBuilder
import java.io.IOException

class UserRemoteDataSource(
    private val retrofit: RetrofitBuilder
) : IUserRemoteDataSource {

    override suspend fun login(username: String, password: String): NetworkResult<User> {
        return try {
            val request = LoginRequest(username, password)
            val response = retrofit.mtgApiService.login(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body.toModel())
                } else {
                    NetworkResult.Error("Respuesta vacía del servidor")
                }
            } else {
                NetworkResult.Error("Error HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: IOException) {
            NetworkResult.Error("Error de conexión: ${e.message}")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado: ${e.message}")
        }
    }
}
