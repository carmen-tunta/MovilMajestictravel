package com.mtg.domain

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val title: String,
    val message: String,
    val channelId: String = "default_channel",
    val notificationId: Int = System.currentTimeMillis().toInt()
)
