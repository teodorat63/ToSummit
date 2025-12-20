package com.example.mobileapp.data.model
import com.google.firebase.Timestamp

data class LocationObject(
    val id: String = "",
    val type: LocationType = LocationType.OTHER,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val description: String = "",
    val authorId: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val photoUrl: String = ""
)

enum class LocationType {
    SUMMIT,
    SHELTER,
    WATER,
    VIEWPOINT,
    OTHER
}

