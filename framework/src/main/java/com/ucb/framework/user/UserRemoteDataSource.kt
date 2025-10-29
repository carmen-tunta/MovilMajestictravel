package com.ucb.framework.user

import com.ucb.data.NetworkResult
import com.ucb.data.user.IUserRemoteDataSource
import com.ucb.domain.User
import com.ucb.framework.dto.user.UserResponseDto
import com.ucb.framework.mappers.toModel
import com.ucb.framework.service.LoginRequest
import com.ucb.framework.service.RetrofitBuilder

class UserRemoteDataSource(
    val retrofit: RetrofitBuilder,
): IUserRemoteDataSource {
    override suspend fun login(username: String, password: String): NetworkResult<User> {
        val request = LoginRequest(username, password)
        val response = retrofit.mtgApiService.login(request)
        println("RESPUESTA" + response)
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body()!!.toModel())
        } else {
            NetworkResult.Error(response.message())
        }
    }
}