package com.example.mobileapp.screens

sealed class Screen(val route: String) {

    object LoginScreen : Screen("login")
    object RegisterScreen : Screen("register")
    object DashboardScreen: Screen("dashboard")

    object LeaderboardScreen: Screen("leaderboard")

    object MapScreen: Screen("map")
}