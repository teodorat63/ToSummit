package com.example.mobileapp.utils

import androidx.compose.ui.graphics.Color
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationType

data class LocationTypeInfo(
    val drawableRes: Int,
    val color: Color,
    val label: String
)

fun getLocationTypeInfo(type: LocationType): LocationTypeInfo = when (type) {
    LocationType.SUMMIT -> LocationTypeInfo(
        drawableRes = R.drawable.ic_marker_summit,
        color = Color(0xFF10B981),
        label = "Summit"
    )
    LocationType.WATER -> LocationTypeInfo(
        drawableRes = R.drawable.ic_marker_water,
        color = Color(0xFF3B82F6),
        label = "Water"
    )
    LocationType.SHELTER -> LocationTypeInfo(
        drawableRes = R.drawable.ic_marker_shelter,
        color = Color(0xFFF59E0B),
        label = "Shelter"
    )
    LocationType.VIEWPOINT -> LocationTypeInfo(
        drawableRes = R.drawable.ic_marker_viewpoint,
        color = Color(0xFF8B5CF6),
        label = "Viewpoint"
    )
    LocationType.OTHER -> LocationTypeInfo(
        drawableRes = R.drawable.ic_marker_other,
        color = Color(0xFF64748B),
        label = "Other"
    )
    LocationType.PARKING -> LocationTypeInfo(
        drawableRes = R.drawable.ic_parking,
        color = Color(0xFF6B7280),
        label = "Parking"
    )
}
