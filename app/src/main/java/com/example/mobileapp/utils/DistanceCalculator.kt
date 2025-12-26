package com.example.mobileapp.utils

import kotlin.math.round

object DistanceCalculator {

    fun distanceInMeters(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Float {
        val result = FloatArray(1)
        android.location.Location.distanceBetween(
            lat1, lon1, lat2, lon2, result
        )
        return round(result[0])
    }
}
