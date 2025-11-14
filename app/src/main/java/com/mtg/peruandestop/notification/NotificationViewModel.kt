package com.mtg.peruandestop.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtg.usecases.push.SendTestNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val sendTestNotification: SendTestNotification
): ViewModel() {

    sealed class NotificationState {
        object Init: NotificationState()
        object Sending: NotificationState()
        object Success: NotificationState()
        data class Error(val message: String): NotificationState()
    }

    private val _notificationState = MutableStateFlow<NotificationState>(NotificationState.Init)
    val notificationState: StateFlow<NotificationState> = _notificationState

    fun sendNotification(title: String, message: String) {
        _notificationState.value = NotificationState.Sending
        viewModelScope.launch {
            try {
                val result = sendTestNotification.invoke(title, message)
                if (result) {
                    _notificationState.value = NotificationState.Success
                } else {
                    _notificationState.value = NotificationState.Error("No se pudo enviar la notificación")
                }
            } catch (e: Exception) {
                _notificationState.value = NotificationState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetState() {
        _notificationState.value = NotificationState.Init
    }
}
