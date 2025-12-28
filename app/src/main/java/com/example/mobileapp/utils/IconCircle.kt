package com.example.mobileapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun IconCircle(iconRes: Int, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .size(36.dp) // Circle size
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = iconRes,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}
