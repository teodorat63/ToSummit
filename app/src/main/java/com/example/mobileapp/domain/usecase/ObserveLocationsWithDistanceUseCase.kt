package com.example.mobileapp.domain.usecase

import com.example.mobileapp.data.repository.LocationRepository
import com.example.mobileapp.domain.model.LocationWithDistance
import com.example.mobileapp.utils.DistanceCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

//combine() = reacts to location OR Firestore changes
//
//distance is calculated once
//
//reusable everywhere
class ObserveLocationsWithDistanceUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    operator fun invoke(): Flow<List<LocationWithDistance>> {
        return combine(
            repository.getLocationUpdates(),
            repository.locationObjects
        ) { userLocation, locations ->

            locations.map { poi ->
                val distance = DistanceCalculator.distanceInMeters(
                    userLocation.latitude,
                    userLocation.longitude,
                    poi.latitude,
                    poi.longitude
                )

                LocationWithDistance(
                    location = poi,
                    distanceMeters = distance
                )
            }
        }
    }
}
