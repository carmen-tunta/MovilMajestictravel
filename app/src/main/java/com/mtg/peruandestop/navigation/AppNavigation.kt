package com.mtg.peruandestop.navigation

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
import com.mtg.framework.datastore.LoginDataSource
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.mtg.peruandestop.bandeja.BandejaScreen
import com.mtg.peruandestop.login.LoginScreen
import com.mtg.peruandestop.notification.NotificationScreen

@Composable
fun AppNavigation(loginDataSource: LoginDataSource) {
    val navController = rememberNavController()
    val currentUser by loginDataSource.userFlow.collectAsState(initial = null)

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
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.NotificationScreen.route)
                }
            )
        }

        composable(Screen.NotificationScreen.route) {
            NotificationScreen()
        }

    }


}