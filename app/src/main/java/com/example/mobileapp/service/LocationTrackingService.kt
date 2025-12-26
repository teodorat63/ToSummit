package com.example.mobileapp.service

import android.location.Location
import android.os.Looper
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.mobileapp.data.repository.LocationRepository
import com.example.mobileapp.utils.DistanceCalculator
import com.example.mobileapp.utils.NotificationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationTrackingService : LifecycleService() {

    @Inject lateinit var repository: LocationRepository

    private val fusedClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    // Track POIs currently inside 100m radius to prevent spamming
    private val currentlyInside = mutableSetOf<String>()

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val LOCATION_UPDATE_INTERVAL = 10_000L // 10 seconds
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(
            FOREGROUND_NOTIFICATION_ID,
            NotificationHelper.nearbyNotification(
                this,
                com.example.mobileapp.data.model.LocationObject(
                    id = "tracking",
                    title = "ToSummit tracking active",
                    latitude = 0.0,
                    longitude = 0.0
                ),
                distance = 0f
            )
        )

        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL
        ).setMinUpdateDistanceMeters(1f)
            .build()

        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation ?: return
            handleLocation(location)
        }
    }

    private fun handleLocation(userLocation: Location) {
        lifecycleScope.launch {
            repository.observeLocationObjects().collect { locations ->
                locations.forEach { poi ->
                    val distance = DistanceCalculator.distanceInMeters(
                        userLocation.latitude,
                        userLocation.longitude,
                        poi.latitude,
                        poi.longitude
                    )

                    if (distance <= 100f) {
                        if (!currentlyInside.contains(poi.id)) {
                            currentlyInside.add(poi.id)

                            val notification = NotificationHelper.nearbyNotification(
                                this@LocationTrackingService,
                                poi,
                                distance
                            )

                            NotificationManagerCompat.from(this@LocationTrackingService)
                                .notify(poi.id.hashCode(), notification)
                        }
                    } else {
                        currentlyInside.remove(poi.id)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedClient.removeLocationUpdates(locationCallback)
    }
}
