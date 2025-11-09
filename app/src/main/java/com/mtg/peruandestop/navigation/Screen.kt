package com.mtg.peruandestop.navigation

sealed class Screen(
    val route: String
) {
    object LoginScreen : Screen("login")
    object BandejaScreen : Screen("bandeja")
}
