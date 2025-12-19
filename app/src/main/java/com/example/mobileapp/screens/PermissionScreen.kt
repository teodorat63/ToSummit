package com.example.mobileapp.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PermissionScreen(onPermissionGranted: () -> Unit) {
    var permissionsGranted by remember { mutableStateOf(false) }

    val galleryPermission = Manifest.permission.READ_MEDIA_IMAGES

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val locationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val galleryGranted = permissions[galleryPermission] == true
        permissionsGranted = locationGranted && galleryGranted
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!permissionsGranted) {
            Text("This app requires Location and Gallery permissions.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Must be triggered by user action on Android 14+
                launcher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        galleryPermission
                    )
                )
            }) {
                Text("Grant Permissions")
            }
        } else {
            LaunchedEffect(Unit) {
                onPermissionGranted()
            }
        }
    }
}

