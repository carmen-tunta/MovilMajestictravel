package com.mtg.usecases.push

import com.mtg.data.PushNotificationRepository
import com.mtg.domain.Notification

class SendTestNotification(
    private val pushRepo: PushNotificationRepository
) {
    suspend fun invoke(title: String, message: String): Boolean {
        val notification = Notification(
            title = title,
            message = message
        )
        return pushRepo.sendLocalNotification(notification)
    }
}
