package com.mtg.framework.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "FirebaseService"
        private const val CHANNEL_ID = "mtg_client_notifications"
        private const val CHANNEL_NAME = "Nuevos Clientes"
        private const val CHANNEL_DESCRIPTION = "Notificaciones cuando se asigna un nuevo cliente"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }
            
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed FCM token: $token")
        
        // TODO: Aquí debes enviar el token al servidor backend
        // para que el servidor pueda enviar notificaciones push a este dispositivo
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        // TODO: Implementar llamada al backend para registrar el token FCM
        // Por ejemplo: POST /api/users/fcm-token con { "token": token, "userId": userId }
        Log.d(TAG, "Token FCM que debe enviarse al servidor: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Mensaje recibido de: ${remoteMessage.from}")

        // Obtener datos del mensaje
        val data = remoteMessage.data
        val notification = remoteMessage.notification

        // Priorizar datos personalizados
        val title = data["title"] ?: notification?.title ?: "Nuevo Cliente"
        val body = data["body"] ?: notification?.body ?: "Tienes un nuevo cliente asignado"
        val clientName = data["clientName"] ?: ""

        Log.d(TAG, "Título: $title, Mensaje: $body")

        // Mostrar notificación
        showNotification(title, body, clientName)
    }

    private fun showNotification(title: String, body: String, clientName: String) {
        // Verificar permiso para Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.w(TAG, "POST_NOTIFICATIONS permission not granted")
                return
            }
        }

        // Intent para abrir la app cuando se toca la notificación
        val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 500, 200, 500))

        // Si hay nombre del cliente, agregarlo como línea adicional
        if (clientName.isNotEmpty()) {
            notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$body\n\nCliente: $clientName")
            )
        }

        with(NotificationManagerCompat.from(this)) {
            notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        }

        Log.d(TAG, "Notificación mostrada exitosamente")
    }
}
