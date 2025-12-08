package com.example.mobileapp.screens;


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun DashboardScreen(navController: NavController) {
    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome text
        Text(
            text = "Welcome to ToSummit.\nYou have successfully logged in.",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        // Logout button
        Button(
            onClick = {
                // Navigate back to login screen or handle logout logic
                navController.navigate("login") {
                    popUpTo("dashboard") { inclusive = true }
                }
            }
        ) {
            Text("Log Out")
        }
    }
}
