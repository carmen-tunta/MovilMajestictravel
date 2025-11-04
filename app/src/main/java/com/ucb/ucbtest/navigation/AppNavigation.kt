package com.ucb.ucbtest.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.framework.datastore.LoginDataSource
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ucb.ucbtest.bandeja.BandejaScreen
import com.ucb.ucbtest.login.LoginScreen

@Composable
fun AppNavigation(loginDataSource: LoginDataSource) {
    val navController = rememberNavController()
    val currentUser by loginDataSource.userFlow.collectAsState(initial = null)

    if (currentUser == null && !loginDataSource.isInitialized()) {
        // Mostrar una pantalla de carga, logo, splash, etc.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (currentUser != null) {
        Screen.BandejaScreen.route
    } else {
        Screen.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.BandejaScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.BandejaScreen.route) {
            BandejaScreen(
                onLogout = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.BandejaScreen.route) { inclusive = true }
                    }
                }
            )
        }

    }


}