package com.example.mobileapp.screens.Location

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.screens.location.dialogs.LocationDetailsBottomSheet
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
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
                radius = 1.0,
                strokeColor = Color.Red,
                fillColor = Color.Red
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
        LocationDetailsBottomSheet (it, onDismissDetails)
    }
}
