package com.mtg.framework.push

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.mtg.data.push.IPushDataSource
import com.mtg.domain.Notification
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseNotificationDataSource(
    private val context: Context
): IPushDataSource {
    
    companion object {
        private const val CHANNEL_ID = "mtg_notifications"
        private const val CHANNEL_NAME = "MTG Notifications"
        private const val CHANNEL_DESCRIPTION = "Notificaciones de Majestic Travel Group"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override suspend fun getNoti(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FIREBASE", "getInstanceId failed", task.exception)
                    continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
                    return@addOnCompleteListener
                }
                // Si la tarea fue exitosa, se obtiene el token
                val token = task.result
                Log.d("FIREBASE", "FCM Token: $token")

                // Reanudar la ejecución con el token
                continuation.resume(token ?: "")
            }
    }

    override suspend fun sendLocalNotification(notification: Notification): Boolean {
        return try {
            // Verificar permiso para Android 13+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.w("FIREBASE", "POST_NOTIFICATIONS permission not granted")
                    return false
                }
            }

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(notification.title)
                .setContentText(notification.message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(notification.notificationId, builder.build())
            }
            
            Log.d("FIREBASE", "Notification sent: ${notification.title}")
            true
        } catch (e: Exception) {
            Log.e("FIREBASE", "Error sending notification", e)
            false
        }
    }
}