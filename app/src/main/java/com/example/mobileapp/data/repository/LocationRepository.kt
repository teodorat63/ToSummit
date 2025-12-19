package com.example.mobileapp.data.repository

import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.example.mobileapp.data.model.LocationObject
import com.google.android.gms.location.*
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val firestore = FirebaseFirestore.getInstance()

    private val _locationObjects = MutableStateFlow<List<LocationObject>>(emptyList())
    val locationObjects: StateFlow<List<LocationObject>> = _locationObjects

    init {
        firestore.collection("location_objects")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                snapshot?.let {
                    val list = it.documents.mapNotNull { doc ->
                        doc.toObject(LocationObject::class.java)
                    }
                    _locationObjects.value = list
                }
            }
    }

    fun addLocationObject(obj: LocationObject) {
        firestore.collection("location_objects")
            .document(obj.id)
            .set(obj)
            .addOnSuccessListener {
                Log.d("Firestore", "LocationObject added successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to add LocationObject", e)
            }
    }

    @Suppress("MissingPermission")
    fun getLocationUpdates(): Flow<Location> = callbackFlow {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000L
        ).setMinUpdateDistanceMeters(1f).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.forEach { trySend(it) }
            }
        }

        fusedLocationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
        awaitClose { fusedLocationClient.removeLocationUpdates(callback) }
    }
}

