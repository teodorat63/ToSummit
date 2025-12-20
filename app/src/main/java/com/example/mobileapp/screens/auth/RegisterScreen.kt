package com.example.mobileapp.screens.auth

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobileapp.screens.Screen

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.registerState
    val context = LocalContext.current

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onRegisterPhotoChange(uri)
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            kotlinx.coroutines.delay(500)
            navController.navigate(Screen.MapScreen.route) {
                popUpTo(Screen.RegisterScreen.route) { inclusive = true }
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
            onValueChange = viewModel::onRegisterEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // PASSWORD
        TextField(
            value = state.password,
            onValueChange = viewModel::onRegisterPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // FIRST NAME
        TextField(
            value = state.firstName,
            onValueChange = viewModel::onRegisterFirstNameChange,
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // LAST NAME
        TextField(
            value = state.lastName,
            onValueChange = viewModel::onRegisterLastNameChange,
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // PHONE
        TextField(
            value = state.phone,
            onValueChange = viewModel::onRegisterPhoneChange,
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // IMAGE PICKER
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Choose Photo")
        }

        // Show selected photo preview
        state.photoUri?.let { uri ->
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        // REGISTER BUTTON
        Button(
            onClick = { viewModel.register() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Registering..." else "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // NAVIGATE TO LOGIN
        TextButton(onClick = { navController.navigate(Screen.LoginScreen.route) }) {
            Text("Already have an account? Login")
        }

        // SHOW ERROR
        state.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
