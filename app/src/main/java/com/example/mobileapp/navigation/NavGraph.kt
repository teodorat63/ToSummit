package com.example.mobileapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapp.screens.PermissionScreen
import com.example.mobileapp.screens.Screen
import com.example.mobileapp.screens.auth.AuthViewModel
import com.example.mobileapp.screens.auth.LoginScreen
import com.example.mobileapp.screens.auth.RegisterScreen
import com.example.mobileapp.screens.leaderboard.LeaderboardScreen
import com.example.mobileapp.screens.list.LocationListScreen
import com.example.mobileapp.screens.location.LocationViewModel
import com.example.mobileapp.screens.location.MapScreen
import com.example.mobileapp.ui.components.AppScaffold

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    AppScaffold(navController) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.LoginScreen.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.LoginScreen.route) {
                val vm: AuthViewModel = hiltViewModel()
                LoginScreen(vm, navController)
            }

            composable(Screen.RegisterScreen.route) {
                val vm: AuthViewModel = hiltViewModel()
                RegisterScreen(vm, navController)
            }

            composable(Screen.MapScreen.route) {
                MapScreen()
            }

            composable(Screen.LeaderboardScreen.route) {
                LeaderboardScreen()
            }

            composable(Screen.ListScreen.route) {
                val viewModel: LocationViewModel = hiltViewModel()
                LocationListScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun NavGraphOld() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MapScreen.route
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


        composable(Screen.LeaderboardScreen.route){ LeaderboardScreen() }
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
