package com.example.mobileapp.screens.location.dialogs

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationType
import com.example.mobileapp.ui.components.InputCard
import com.example.mobileapp.utils.getLocationTypeInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationFullScreenDialog(
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
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Add New Location") },
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        }
                    )
                },
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = onConfirm,
                            enabled = name.isNotBlank(),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add Location")
                        }
                    }
                }
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // TITLE
                    item {
                        InputCard {
                            Text("Title", style = MaterialTheme.typography.labelMedium)
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = name,
                                onValueChange = onNameChange,
                                placeholder = { Text("Add name of this location") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // DESCRIPTION
                    item {
                        InputCard {
                            Text("Description", style = MaterialTheme.typography.labelMedium)
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = description,
                                onValueChange = onDescriptionChange,
                                placeholder = { Text("Add details about this location...") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )
                        }
                    }

                    // LOCATION TYPE GRID
                    item {
                        InputCard {
                            Text("Location Type", style = MaterialTheme.typography.labelMedium)
                            Spacer(Modifier.height(12.dp))

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier.height(180.dp),                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(LocationType.values()) { item ->
                                    val info = getLocationTypeInfo(item)
                                    LocationTypeItem(
                                        type = item,
                                        selected = item == type,
                                        onClick = { onTypeChange(item) },
                                    )
                                }
                            }
                        }
                    }

                    // PHOTO PICKER
                    item {
                        InputCard {
                            Text("Photo (Optional)", style = MaterialTheme.typography.labelMedium)
                            Spacer(Modifier.height(12.dp))

                            if (photoUri != null) {
                                Box {
                                    AsyncImage(
                                        model = photoUri,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                            .clip(RoundedCornerShape(20.dp)),
                                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                    )
                                }
                            } else {
                                OutlinedButton(
                                    onClick = onPickPhoto,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_marker_other),
                                            contentDescription = null
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text("Upload from Gallery")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
private fun LocationTypeItem(
    type: LocationType,
    selected: Boolean,
    onClick: () -> Unit
) {
    val info = getLocationTypeInfo(type)

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) info.color.copy(alpha = 0.2f) else Color(0xFFF5F5F5))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(info.color.copy()),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = info.drawableRes),
                    contentDescription = info.label,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = info.label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
        }

        if (selected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Selected",
                tint = info.color,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            )
        }
    }
}





