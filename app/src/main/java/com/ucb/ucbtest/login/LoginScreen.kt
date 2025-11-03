package com.ucb.ucbtest.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    LoginUI(
        onSuccess = { user ->
            // Guardar usuario en DataStore
            viewModel.saveUserLocally(user)
            onLoginSuccess()
        }
    )
}