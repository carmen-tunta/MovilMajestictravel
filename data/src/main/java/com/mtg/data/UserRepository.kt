package com.mtg.data

import com.mtg.data.user.IUserRemoteDataSource
import com.mtg.domain.User

class UserRepository(
    private val remoteDS: IUserRemoteDataSource
) {
    suspend fun login(
        username: String,
        password: String,
    ): NetworkResult<User> = this.remoteDS.login(username, password)
}
