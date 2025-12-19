package com.example.mobileapp.screens.Location

import android.content.res.Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(viewModel: LocationViewModel = hiltViewModel()) {
    val location by viewModel.location.collectAsState()
    val locationObjects by viewModel.locationObjects.collectAsState()
    val isDialogVisible by viewModel.isDialogVisible.collectAsState()
    val nameInput by viewModel.nameInput.collectAsState()
    val descriptionInput by viewModel.descriptionInput.collectAsState()

    val cameraPositionState = rememberCameraPositionState()
    var hasCentered by remember { mutableStateOf(false) }

    // Animate camera only once on first location
    LaunchedEffect(location) {
        location?.let { loc ->
            if (!hasCentered) {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(loc.latitude, loc.longitude), 17f
                    ),
                    durationMs = 1000
                )
                hasCentered = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // MAP
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            location?.let { loc ->
                // User location: visible, contrasting circle
                Circle(
                    center = LatLng(loc.latitude, loc.longitude),
                    radius = 1.0,
                    strokeColor = Color.Red,
                    strokeWidth = 1f,
                    fillColor = Color.Red
                )
            }

            locationObjects.forEach { obj ->
                Marker(
                    state = MarkerState(LatLng(obj.latitude, obj.longitude)),
                    title = obj.title,
                    snippet = obj.description
                )
            }
        }

        FloatingActionButton(
            onClick = { viewModel.showDialog() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Location")
        }
    }


    if (isDialogVisible) {
        AddLocationDialog(
            name = nameInput,
            description = descriptionInput,
            onNameChange = viewModel::onNameChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onDismiss = viewModel::hideDialog,
            onConfirm = viewModel::addLocationObject
        )
    }
}

@Composable
fun AddLocationDialog(
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Location Object") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    singleLine = true,
                    placeholder = { Text("Enter a name for this location") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    label = { Text("Description") },
                    placeholder = { Text("Optional description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}





