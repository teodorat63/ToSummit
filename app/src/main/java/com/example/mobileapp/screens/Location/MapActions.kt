package com.example.mobileapp.screens.Location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileapp.data.model.LocationType
import getLocationTypeColor

@Composable
fun MapActions(
    onAddClick: () -> Unit,
    onFilterClick: () -> Unit,
    onListClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Add Location (Bottom End)
        FloatingActionButton(
            onClick = onAddClick,
            containerColor = Color(getLocationTypeColor(LocationType.SUMMIT)),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Location", tint = Color.White)
        }

        // Filter Locations (Bottom Start)
        FloatingActionButton(
            onClick = onFilterClick,
            containerColor = Color(getLocationTypeColor(LocationType.WATER)),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(Icons.Default.Face, contentDescription = "Filter Locations", tint = Color.White)
        }

        // List Locations (Top End)
        FloatingActionButton(
            onClick = onListClick,
            containerColor = Color(getLocationTypeColor(LocationType.VIEWPOINT)),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.List, contentDescription = "List Locations", tint = Color.White)
        }
    }
}

