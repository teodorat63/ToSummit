package com.example.mobileapp.screens.location

import com.example.mobileapp.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.theme.Blue600
import com.example.mobileapp.ui.theme.Emerald600

@Composable
fun MapActions(
    onAddClick: () -> Unit,
    onFilterClick: () -> Unit,

) {
    Box(modifier = Modifier.fillMaxSize()) {

        FloatingActionButton(
            onClick = onAddClick,
            containerColor = Emerald600,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Location", tint = Color.White)
        }

        FloatingActionButton(
            onClick = onFilterClick,
            containerColor = Blue600,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
            )
        }



    }
}



