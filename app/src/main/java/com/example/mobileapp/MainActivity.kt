package com.example.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.screens.auth.AuthViewModel
import com.example.mobileapp.screens.auth.LoginScreen
import com.example.mobileapp.screens.auth.RegisterScreen
import com.example.mobileapp.ui.theme.MobileAppTheme
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    private val authRepository by lazy { AuthRepository(FirebaseAuth.getInstance()) }
    private val authViewModel by lazy { AuthViewModel(authRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(authViewModel)
        }
    }
}


@Composable
fun MyApp(viewModel: AuthViewModel) {
    MobileAppTheme {
        LoginScreen(viewModel = viewModel, onRegisterClick = {
            // Handle registration click
        })
    }
}



