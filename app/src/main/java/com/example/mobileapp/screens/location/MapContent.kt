package com.example.mobileapp.screens.location

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.screens.location.dialogs.LocationDetailsBottomSheet
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
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
            Marker(
                state = MarkerState(
                    position = LatLng(it.latitude, it.longitude)
                ),
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_userlocation)
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

    selectedObject?.let { obj ->
        LocationDetailsBottomSheet(
            location = obj,
            userLocation = location,
            onClose = onDismissDetails
        )
    }

}
