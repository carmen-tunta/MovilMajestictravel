package com.mtg.usecases.push

import com.mtg.data.PushNotificationRepository

class UnregisterFCMToken(
    private val pushRepo: PushNotificationRepository
) {
    suspend fun invoke(): Boolean {
        return try {
            // Obtener el token FCM actual
            val token = pushRepo.getFCMToken()
            
            // Eliminarlo del servidor
            pushRepo.removeTokenFromServer(token)
        } catch (e: Exception) {
            false
        }
    }
}
