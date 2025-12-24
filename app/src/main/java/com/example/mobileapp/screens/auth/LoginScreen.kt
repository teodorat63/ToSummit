package com.example.mobileapp.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobileapp.R
import com.example.mobileapp.screens.Screen
import com.example.mobileapp.ui.components.AppButton
import com.example.mobileapp.ui.components.AppOutlinedButton
import com.example.mobileapp.ui.components.InputField
import com.example.mobileapp.ui.theme.Blue50
import com.example.mobileapp.ui.theme.Emerald50
import com.example.mobileapp.ui.theme.Emerald600
import com.example.mobileapp.ui.theme.Emerald700
import com.example.mobileapp.ui.theme.PaddingLarge
import com.example.mobileapp.ui.theme.PaddingMedium
import com.example.mobileapp.ui.theme.PaddingSmall
import com.example.mobileapp.ui.theme.Red600
import com.example.mobileapp.ui.theme.Slate200
import com.example.mobileapp.ui.theme.Slate400

@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavController) {

    val state = viewModel.loginState

    var showPassword by remember { mutableStateOf(false) }

    LaunchedEffect(state.success) {
        if (state.success) {
            navController.navigate(Screen.MapScreen.route) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
        }
    }

    // Background gradient
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Emerald50, Blue50)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Emerald600, Emerald700)
                        )
                    )
                    .padding(top = 48.dp, bottom = 32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(40.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground), // use your launcher resource
                            contentDescription = "App Icon",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("ToSummit", color = Color.White, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Welcome back", color = Emerald50)
                }
            }

            // Login Form
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PaddingLarge, vertical = PaddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(PaddingMedium))

                // Email
                InputField(
                    value = state.email,
                    onValueChange = viewModel::onLoginEmailChange,
                    label = "Email"
                )

                Spacer(modifier = Modifier.height(PaddingMedium))

                // Password
                InputField(
                    value = state.password,
                    onValueChange = viewModel::onLoginPasswordChange,
                    label = "Password",
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { showPassword = !showPassword }) {
                            Text(if (showPassword) "Hide" else "Show")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(PaddingSmall))

                // Forgot password
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(onClick = {  }) {
                        Text("Forgot password?", color = Emerald600)
                    }
                }

                Spacer(modifier = Modifier.height(PaddingMedium))

                // Login Button
                AppButton(
                    text = if (state.isLoading) "Logging in..." else "Log In",
                    onClick = { viewModel.login() },
                    enabled = !state.isLoading
                )

                // Error message
                state.error?.let {
                    Spacer(modifier = Modifier.height(PaddingMedium))
                    Text(text = it, color = Red600)
                }

                Spacer(modifier = Modifier.height(PaddingMedium))

                // Divider with text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Slate200)
                    Text(
                        text = "  New to ToSummit?  ",
                        color = Slate400
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Slate200)
                }

                Spacer(modifier = Modifier.height(PaddingMedium))

                // Create Account Button
                AppOutlinedButton(
                    text = "Create Account",
                    onClick = { navController.navigate(Screen.RegisterScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(PaddingLarge))
            }
        }
    }
}
