package com.mtg.usecases.push

import com.mtg.data.PushNotificationRepository

class SendNewClientNotification(
    private val pushRepo: PushNotificationRepository
) {
    suspend fun invoke(clientName: String): Boolean {
        return pushRepo.sendLocalNotification(
            com.mtg.domain.Notification(
                title = "Nuevo Cliente Asignado 🎉",
                message = "Se te ha asignado un nuevo cliente: $clientName"
            )
        )
    }
}
