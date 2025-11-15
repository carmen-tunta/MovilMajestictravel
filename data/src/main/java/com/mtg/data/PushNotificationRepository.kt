package com.mtg.data

import com.mtg.data.push.IPushDataSource
import com.mtg.domain.Notification

class PushNotificationRepository(
    val push: IPushDataSource
) {

    suspend fun getFCMToken(): String {
        return push.getFCMToken()
    }

    suspend fun sendLocalNotification(notification: Notification): Boolean {
        return push.sendLocalNotification(notification)
    }

    suspend fun sendTokenToServer(userId: String, token: String): Boolean {
        return push.sendTokenToServer(userId, token)
    }

    suspend fun removeTokenFromServer(token: String): Boolean {
        return push.removeTokenFromServer(token)
    }
}