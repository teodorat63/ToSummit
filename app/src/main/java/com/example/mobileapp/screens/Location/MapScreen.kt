package com.example.mobileapp.screens.Location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import com.google.android.gms.maps.CameraUpdateFactory

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen(viewModel: LocationViewModel = hiltViewModel()) {
    val location by viewModel.location.collectAsState()

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(location) {
        location?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude), 17f
                ),
                durationMs = 1000
            )
        }
    }

    // Google Map Composable
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        location?.let {
            Marker(
                state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                title = "Your Location"
            )
        }
    }
}
