package com.mtg.data.user

import com.mtg.data.NetworkResult
import com.mtg.domain.User

interface IUserRemoteDataSource {
    suspend fun login(
        username: String,
        password: String,
    ): NetworkResult<User>
}
