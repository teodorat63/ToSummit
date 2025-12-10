package com.example.mobileapp.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileapp.screens.Screen

@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavController) {

    val state = viewModel.loginState

    LaunchedEffect(state.success) {
        if (state.success) {
            navController.navigate(Screen.MapScreen.route) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // EMAIL
        TextField(
            value = state.email,
            onValueChange = viewModel::onLoginEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // PASSWORD
        TextField(
            value = state.password,
            onValueChange = viewModel::onLoginPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // LOGIN BUTTON
        Button(
            onClick = { viewModel.login() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Logging in..." else "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // NAVIGATE TO REGISTER
        TextButton(
            onClick = { navController.navigate(Screen.RegisterScreen.route) }
        ) {
            Text("Don't have an account? Register")
        }

        // ERROR MESSAGE
        state.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
