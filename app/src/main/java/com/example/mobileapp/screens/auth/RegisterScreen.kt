package com.example.mobileapp.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun RegisterScreen(viewModel: AuthViewModel, onLoginClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp
            ),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = viewModel.confirmPassword,
//            onValueChange = viewModel::onConfirmPasswordChange,
//            label = { Text("Confirm Password") },
//            visualTransformation = PasswordVisualTransformation()
//        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.register() }, modifier = Modifier.fillMaxWidth()) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Login")
        }
        val error = viewModel.errorMessage
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}
