package com.mtg.data

import com.mtg.data.push.IPushDataSource

class PushNotificationRepository(
    val push: IPushDataSource
) {

    suspend fun getToken(): String {
        return push.getToken()
    }
}