package com.example.mobileapp.screens

sealed class Screen(val route: String) {

    object LoginScreen : Screen("login")
    object RegisterScreen : Screen("register")
}