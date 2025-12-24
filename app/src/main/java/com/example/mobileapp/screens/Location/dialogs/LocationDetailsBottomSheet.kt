package com.example.mobileapp.screens.Location.dialogs

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.mobileapp.utils.getLocationTypeInfo

@Composable
fun LocationDetailsBottomSheet(
    location: LocationObject?,
    onClose: () -> Unit
) {
    if (location == null) return
    val typeInfo = getLocationTypeInfo(location.type)

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
                                .background(Color.Gray)
                        )

                        // Scrollable content
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxSize()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
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

                            // Info rows
                            InfoRow("Author", location.authorName, R.drawable.ic_user)
                            InfoRow("Coordinates", "${location.latitude}, ${location.longitude}", R.drawable.ic_map)
                            InfoRow("Created", location.createdAt.toDate().toString(), R.drawable.ic_calendar)
                        }
                    }
                }
            }
        }
    }
}



@Composable
private fun InfoRow(label: String, value: String, iconRes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        AsyncImage(
            model = iconRes,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
