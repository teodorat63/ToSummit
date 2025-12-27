package com.example.mobileapp.service

import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.mobileapp.data.repository.LocationRepository
import com.example.mobileapp.domain.usecase.ObserveLocationsWithDistanceUseCase
import com.example.mobileapp.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationTrackingService : LifecycleService() {

    @Inject lateinit var repository: LocationRepository
    @Inject lateinit var observeLocationsWithDistanceUseCase: ObserveLocationsWithDistanceUseCase

    private val currentlyInside = mutableSetOf<String>()

    override fun onCreate() {
        super.onCreate()

        startForeground(
            1,
            NotificationHelper.nearbyNotification(
                this,
                com.example.mobileapp.data.model.LocationObject(
                    id = "tracking",
                    title = "Tracking Active",
                    latitude = 0.0,
                    longitude = 0.0
                ),
                distance = 0f
            )
        )

        lifecycleScope.launch {
            observeLocationsWithDistanceUseCase().collect { locationsWithDistance ->
                locationsWithDistance.forEach { item ->
                    handleNearbyNotification(item)
                }
            }
        }
    }

    private fun handleNearbyNotification(item: com.example.mobileapp.domain.model.LocationWithDistance) {
        if (item.distanceMeters <= 100f) {
            if (!currentlyInside.contains(item.location.id)) {
                currentlyInside.add(item.location.id)

                val notification = NotificationHelper.nearbyNotification(
                    this,
                    item.location,
                    item.distanceMeters
                )

                NotificationManagerCompat.from(this)
                    .notify(item.location.id.hashCode(), notification)
            }
        } else {
            currentlyInside.remove(item.location.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

