package com.ucb.ucbtest.bandeja

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BandejaScreen(viewModel: BandejaViewModel = hiltViewModel()) {
    val user = viewModel.currentUser.collectAsState(initial = null).value

    if (user != null) {
        BandejaUI(user = user)
    } else {
        Text(text = "Redirigiendo...")
    }
}