package com.mtg.peruandestop.bandeja

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtg.peruandestop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BandejaScreen(
    viewModel: BandejaViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val user = viewModel.currentUser.collectAsState(initial = null).value

    if (user != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "PeruAndesTravel Logo",
                            modifier = Modifier
                                .padding(top = 25.dp)
                                .width(160.dp)
                        )
                    },
                    actions = {
                        TextButton(
                            onClick = {
                                viewModel.logout()
                                onLogout()
                            }
                        ) {
                            Text("Cerrar sesión", color = colorResource(R.color.text_color))
                        }
                    }
                )
            }
        ) { padding ->
            // Contenido principal de la bandeja
            Box(modifier = Modifier.padding(padding)) {
                BandejaUI(user = user)
            }
        }
    } else {
        Text(text = "Redirigiendo...")
    }
}
