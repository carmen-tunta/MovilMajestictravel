package com.mtg.data.push

import com.mtg.domain.Notification

interface IPushDataSource {
    suspend fun getNoti(): String
    suspend fun sendLocalNotification(notification: Notification): Boolean
}