package com.example.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.navigation.NavGraph
import com.example.mobileapp.screens.auth.AuthViewModel
import com.example.mobileapp.screens.auth.LoginScreen
import com.example.mobileapp.screens.auth.RegisterScreen
import com.example.mobileapp.ui.theme.MobileAppTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}


@Composable
fun MyApp() {
    MobileAppTheme {
        NavGraph()
    }
}



