package com.mtg.data.push

import com.mtg.domain.Notification

interface IPushDataSource {
    suspend fun getFCMToken(): String
    suspend fun sendLocalNotification(notification: Notification): Boolean
    suspend fun sendTokenToServer(userId: String, token: String): Boolean
}