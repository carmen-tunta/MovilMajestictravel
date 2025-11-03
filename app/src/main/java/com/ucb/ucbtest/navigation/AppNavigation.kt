package com.ucb.ucbtest.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ucb.domain.User
import com.ucb.framework.datastore.LoginDataSource
import com.ucb.ucbtest.bandeja.BandejaUI
import com.ucb.ucbtest.login.LoginUI
import androidx.compose.runtime.getValue
import com.ucb.ucbtest.bandeja.BandejaScreen
import com.ucb.ucbtest.login.LoginScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

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
            BandejaScreen()
        }

    }


}