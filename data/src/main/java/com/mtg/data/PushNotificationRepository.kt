package com.mtg.data

import com.mtg.data.push.IPushDataSource
import com.mtg.domain.Notification

class PushNotificationRepository(
    val push: IPushDataSource
) {

    suspend fun getNoti(): String {
        return push.getNoti()
    }

    suspend fun sendLocalNotification(notification: Notification): Boolean {
        return push.sendLocalNotification(notification)
    }
}