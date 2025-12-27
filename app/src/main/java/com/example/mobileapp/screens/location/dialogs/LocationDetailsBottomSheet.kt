package com.example.mobileapp.screens.location.dialogs


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.ui.theme.Background
import com.example.mobileapp.ui.theme.Emerald600
import com.example.mobileapp.ui.theme.Slate600
import com.example.mobileapp.ui.theme.Violet600
import com.example.mobileapp.utils.DistanceCalculator
import com.example.mobileapp.utils.IconCircle
import com.example.mobileapp.utils.getLocationTypeInfo
import com.example.mobileapp.utils.getRelativeTimeString

@Composable
fun LocationDetailsBottomSheet(
    location: LocationObject?,
    userLocation: android.location.Location?, // User location for distance calculation
    onClose: () -> Unit
) {
    if (location == null) return

    val typeInfo = getLocationTypeInfo(location.type)

    val distanceText = if (userLocation != null) {
        val distanceMeters = DistanceCalculator.distanceInMeters(
            userLocation.latitude,
            userLocation.longitude,
            location.latitude,
            location.longitude
        )
        if (distanceMeters < 1000f) {
            "${distanceMeters.toInt()} m away"
        } else {
            String.format("%.1f km away", distanceMeters / 1000f)
        }
    } else {
        "Distance unknown"
    }

    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Box {
                // Backdrop
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable(onClick = onClose)
                )

                // Bottom sheet
                Surface(
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                        .align(Alignment.BottomCenter),
                    tonalElevation = 8.dp
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        // Drag handle
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 4.dp)
                                .align(Alignment.CenterHorizontally)
                                .size(width = 48.dp, height = 4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Background)
                        )

                        // Scrollable content
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Photo
                            if (location.photoUrl.isNotBlank()) {
                                AsyncImage(
                                    model = location.photoUrl,
                                    contentDescription = location.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                )
                            }

                            // Title & Type
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(location.title, style = MaterialTheme.typography.headlineSmall)

                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = typeInfo.color.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = typeInfo.label,
                                        color = typeInfo.color,
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Description
                            if (location.description.isNotBlank()) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("Description", style = MaterialTheme.typography.labelMedium)
                                    Text(location.description, style = MaterialTheme.typography.bodyMedium)
                                }
                            }

                            InfoRow("Author", location.authorName, R.drawable.ic_user, Emerald600)
                            InfoRow("Distance", distanceText, R.drawable.ic_map, Violet600)
                            InfoRow("Created", getRelativeTimeString(location.createdAt.toDate()), R.drawable.ic_calendar, Slate600)

                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun InfoRow(label: String, value: String, iconRes: Int, backgroundColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        IconCircle(iconRes = iconRes, backgroundColor = backgroundColor)
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
