package com.ucb.data

import com.ucb.data.user.IUserRemoteDataSource
import com.ucb.domain.User

class UserRepository(
    private val remoteDS: IUserRemoteDataSource
) {
    suspend fun login(
        username: String,
        password: String,
    ): NetworkResult<User> = this.remoteDS.login(username, password)
}
