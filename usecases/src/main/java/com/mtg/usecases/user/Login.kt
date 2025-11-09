package com.mtg.usecases.user

import com.mtg.data.NetworkResult
import com.mtg.data.UserRepository
import com.mtg.domain.User

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
