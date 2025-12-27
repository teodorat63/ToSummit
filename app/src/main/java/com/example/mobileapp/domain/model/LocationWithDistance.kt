package com.example.mobileapp.domain.model

import com.example.mobileapp.data.model.LocationObject


data class LocationWithDistance(
    val location: LocationObject,
    val distanceMeters: Float
)
