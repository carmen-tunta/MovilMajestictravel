package com.mtg.usecases.push

import com.mtg.data.PushNotificationRepository

class RegisterFCMToken(
    private val pushRepo: PushNotificationRepository
) {
    suspend fun invoke(userId: String): Boolean {
        return try {
            val token = pushRepo.getFCMToken()
            pushRepo.sendTokenToServer(userId, token)
        } catch (e: Exception) {
            false
        }
    }
}
