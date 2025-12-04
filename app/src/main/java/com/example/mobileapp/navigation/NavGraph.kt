//package com.example.mobileapp.navigation
//
//import com.example.mobileapp.screens.auth.LoginScreen
//import com.example.mobileapp.screens.auth.RegisterScreen
//
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.rememberNavController
//import com.example.mobileapp.screens.auth.AuthViewModel
//import androidx.navigation.compose.composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
//
//
//@Composable
//fun NavGraph() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "login") {
//        composable("login") {
//            val viewModel: AuthViewModel = hiltViewModel()
//            LoginScreen(
//                viewModel = viewModel,
//                onRegisterClick = { navController.navigate("register") }
//            )
//        }
//        composable("register") {
//            val viewModel: AuthViewModel = hiltViewModel()
//            RegisterScreen(
//                viewModel = viewModel,
//                onLoginClick = { navController.navigate("login") }
//            )
//        }
//    }
//}
//
