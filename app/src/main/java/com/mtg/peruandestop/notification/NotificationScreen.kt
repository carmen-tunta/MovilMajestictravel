package com.mtg.peruandestop.notification

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val notificationState by viewModel.notificationState.collectAsState()
    
    var title by remember { mutableStateOf("Nuevo Cliente") }
    var message by remember { mutableStateOf("Tienes un nuevo cliente asignado") }
    
    // Manejar permisos para Android 13+
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Prueba de Notificaciones",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mensaje") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Mostrar estado del permiso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionState?.let { permissionState ->
                if (!permissionState.status.isGranted) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "⚠️ Permiso de notificaciones requerido",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { permissionState.launchPermissionRequest() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Solicitar permiso")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                // Verificar permiso antes de enviar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (notificationPermissionState?.status?.isGranted == true) {
                        viewModel.sendNotification(title, message)
                    } else {
                        notificationPermissionState?.launchPermissionRequest()
                    }
                } else {
                    viewModel.sendNotification(title, message)
                }
            },
            enabled = notificationState !is NotificationViewModel.NotificationState.Sending,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            when (notificationState) {
                is NotificationViewModel.NotificationState.Sending -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                else -> {
                    Text("Enviar Notificación de Prueba")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = notificationState) {
            is NotificationViewModel.NotificationState.Success -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "✓ Notificación enviada exitosamente",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2000)
                    viewModel.resetState()
                }
            }
            is NotificationViewModel.NotificationState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "✗ ${state.message}",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(3000)
                    viewModel.resetState()
                }
            }
            else -> {}
        }
    }
}
