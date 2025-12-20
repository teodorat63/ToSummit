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
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.data.model.LocationType
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import getMarkerIcon


@Composable
fun MapScreen(viewModel: LocationViewModel = hiltViewModel()) {
    val location by viewModel.location.collectAsState()
    val locationObjects by viewModel.locationObjects.collectAsState()
    val isDialogVisible by viewModel.isDialogVisible.collectAsState()
    val nameInput by viewModel.nameInput.collectAsState()
    val descriptionInput by viewModel.descriptionInput.collectAsState()
    val typeInput by viewModel.typeInput.collectAsState()
    val selectedObject by viewModel.selectedObject.collectAsState()
    val photoUri by viewModel.photoUri.collectAsState()



    val cameraPositionState = rememberCameraPositionState()
    var hasCentered by remember { mutableStateOf(false) }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onPhotoSelected(uri)
    }

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
                    snippet = obj.description,
                    icon = getMarkerIcon(context = LocalContext.current, type = obj.type),
                    onClick = {
                        viewModel.onMarkerClick(obj)
                        true
                    }
                )
            }

            selectedObject?.let { obj ->
                LocationDetailsDialog(
                    locationObject = obj,
                    onDismiss = viewModel::clearSelectedObject
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
            type = typeInput,
            photoUri = photoUri,
            onPickPhoto = { galleryLauncher.launch("image/*") },
            onTypeChange = viewModel::onTypeChange,
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
    type: LocationType,
    photoUri: Uri?,
    onPickPhoto: () -> Unit,
    onTypeChange: (LocationType) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Location Object") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                Text("Type", style = MaterialTheme.typography.labelMedium)
                TextButton(onClick = { expanded = true }) {
                    Text(type.name.lowercase().replaceFirstChar { it.uppercase() })
                }

                androidx.compose.material3.DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    LocationType.values().forEach {
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                onTypeChange(it)
                                expanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = onPickPhoto,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Choose Photo")
                }

                photoUri?.let { uri ->
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = uri,
                        contentDescription = "Selected photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }

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

@Composable
fun LocationDetailsDialog(
    locationObject: LocationObject,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(locationObject.title)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                DetailRow("Type", locationObject.type.name)
                DetailRow("Description", locationObject.description)

                DetailRow("Author", locationObject.authorName)
                DetailRow(
                    "Created",
                    locationObject.createdAt.toDate().toString()
                )

                if (locationObject.photoUrl.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = locationObject.photoUrl,
                        contentDescription = "Location photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }

            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}







