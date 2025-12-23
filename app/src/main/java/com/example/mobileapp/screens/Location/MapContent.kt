package com.example.mobileapp.screens.Location

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.screens.Location.dialogs.LocationDetailsBottomSheet
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import getMarkerIcon

@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    location: android.location.Location?,
    locationObjects: List<LocationObject>,
    selectedObject: LocationObject?,
    onMarkerClick: (LocationObject) -> Unit,
    onDismissDetails: () -> Unit
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        location?.let {
            Circle(
                center = LatLng(it.latitude, it.longitude),
                radius = 3.0, // small radius in meters
                strokeColor = Color.White,
                strokeWidth = 3f,
                fillColor = Color(0xFF2196F3) // solid blue fill
            )
        }

            locationObjects.forEach { obj ->
            Marker(
                state = MarkerState(LatLng(obj.latitude, obj.longitude)),
                title = obj.title,
                snippet = obj.description,
                icon = getMarkerIcon(LocalContext.current, obj.type),
                onClick = {
                    onMarkerClick(obj)
                    true
                }
            )
        }
    }

    selectedObject?.let {
        LocationDetailsBottomSheet(it, onDismissDetails)
    }
}
