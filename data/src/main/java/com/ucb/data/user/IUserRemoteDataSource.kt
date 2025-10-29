package com.ucb.data.user

import com.ucb.data.NetworkResult
import com.ucb.domain.User

interface IUserRemoteDataSource {
    suspend fun login(
        username: String,
        password: String,
    ): NetworkResult<User>
}
