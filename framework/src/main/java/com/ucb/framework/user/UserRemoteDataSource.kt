package com.ucb.framework.user

import com.ucb.data.NetworkResult
import com.ucb.data.user.IUserRemoteDataSource
import com.ucb.domain.User
import com.ucb.framework.mappers.toModel
import com.ucb.framework.service.LoginRequest
import com.ucb.framework.service.RetrofitBuilder
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
