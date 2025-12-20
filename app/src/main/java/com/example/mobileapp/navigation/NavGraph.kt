package com.example.mobileapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.screens.Screen
import com.example.mobileapp.screens.auth.AuthViewModel
import com.example.mobileapp.screens.auth.LoginScreen
import com.example.mobileapp.screens.auth.RegisterScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobileapp.screens.DashboardScreen
import com.example.mobileapp.screens.Location.MapScreen
import com.example.mobileapp.screens.PermissionScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = authViewModel,
                navController
            )
        }

        composable(Screen.RegisterScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(viewModel = authViewModel, navController)
        }

        composable(Screen.DashboardScreen.route){ DashboardScreen(navController) }

        composable(Screen.MapScreen.route){ MapScreen() }

        composable("permissions") {
            PermissionScreen(
                onPermissionGranted = {
                    navController.navigate("map") {
                        popUpTo("permissions") { inclusive = true }
                    }
                }
            )
        }
    }
}
