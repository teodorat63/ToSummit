package com.example.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.screens.auth.AuthViewModel
import com.example.mobileapp.screens.auth.LoginScreen
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



