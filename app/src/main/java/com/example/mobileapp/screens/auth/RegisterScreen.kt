package com.example.mobileapp.screens.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobileapp.screens.Screen
import com.example.mobileapp.ui.components.AppButton
import com.example.mobileapp.ui.components.AppOutlinedButton
import com.example.mobileapp.ui.components.InputField
import com.example.mobileapp.ui.components.ProfileImagePicker
import com.example.mobileapp.ui.theme.Blue50
import com.example.mobileapp.ui.theme.Emerald50
import com.example.mobileapp.ui.theme.PaddingMedium
import com.example.mobileapp.ui.theme.PaddingSmall
import com.example.mobileapp.ui.theme.Red600
import com.example.mobileapp.ui.theme.Slate200
import com.example.mobileapp.ui.theme.Slate400

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.registerState

    if (state.success) {
        LaunchedEffect(state.success) {
            navController.navigate(Screen.MapScreen.route) {
                popUpTo(Screen.RegisterScreen.route) { inclusive = true }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> viewModel.onRegisterPhotoChange(uri) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Emerald50, Blue50)
                )
            )
            .padding(PaddingMedium)
    ) {

        var showPassword by remember { mutableStateOf(false) }


        // Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            ProfileImagePicker(
                imageUri = state.photoUri?.toString(),
                onClickPick = { galleryLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(PaddingMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PaddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputField(
                    value = state.firstName,
                    onValueChange = viewModel::onRegisterFirstNameChange,
                    label = "First Name",
                    modifier = Modifier.weight(1f)
                )
                InputField(
                    value = state.lastName,
                    onValueChange = viewModel::onRegisterLastNameChange,
                    label = "Last Name",
                    modifier = Modifier.weight(1f)
                )
            }

            InputField(
                value = state.email,
                onValueChange = viewModel::onRegisterEmailChange,
                label = "Email",
            )

            InputField(
                value = state.phone,
                onValueChange = viewModel::onRegisterPhoneChange,
                label = "Phone"
            )

            InputField(
                value = state.password,
                onValueChange = viewModel::onRegisterPasswordChange,
                label = "Password",
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showPassword = !showPassword }) {
                        Text(if (showPassword) "Hide" else "Show")
                    }
                }
            )

            Spacer(modifier = Modifier.height(PaddingMedium))

            AppButton(
                text = if (state.isLoading) "Registering..." else "Create Account",
                onClick = viewModel::register,
                enabled = !state.isLoading
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Slate200)
                Text(
                    text = "  Already have an account?  ",
                    color = Slate400
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Slate200)
            }

            AppOutlinedButton(
                text = "Log in",
                onClick = { navController.navigate(Screen.LoginScreen.route) },
                modifier = Modifier.fillMaxWidth()
            )


            state.error?.let {
                Spacer(modifier = Modifier.height(PaddingSmall))
                Text(it, color = Red600)
            }
        }
    }
}

