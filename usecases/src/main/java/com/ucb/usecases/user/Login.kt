package com.ucb.usecases.user

import com.ucb.data.NetworkResult
import com.ucb.data.UserRepository
import com.ucb.domain.User

class Login(
    private val userRepo: UserRepository
) {
    suspend fun invoke(
        username: String,
        password: String,
    ): NetworkResult<User> {
        return userRepo.login(username, password)
    }
}
