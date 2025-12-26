package com.example.mobileapp.data.repository

import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.example.mobileapp.data.model.LocationObject
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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

    // -------------------------
    // StateFlow for UI
    // -------------------------
    private val _locationObjects = MutableStateFlow<List<LocationObject>>(emptyList())
    val locationObjects: StateFlow<List<LocationObject>> = _locationObjects

    init {
        // Observe Firestore in real-time
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

    // -------------------------
    // Flow for Notifications
    // -------------------------
    fun observeLocationObjects(): Flow<List<LocationObject>> = callbackFlow {
        val listener = firestore.collection("location_objects")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                val list = snapshot?.documents
                    ?.mapNotNull { it.toObject(LocationObject::class.java) }
                    ?: emptyList()

                trySend(list)
            }

        awaitClose { listener.remove() }
    }

    // -------------------------
    // Add a LocationObject
    // -------------------------
    fun addLocationObject(obj: LocationObject) {
        Log.d("Firestore", "Attempting to add LocationObject with id: ${obj.id} -> $obj")

        firestore.collection("location_objects")
            .document(obj.id)
            .set(obj)
            .addOnSuccessListener {
                Log.d("Firestore", "LocationObject added successfully: ${obj.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Failed to add LocationObject: ${obj.id}", e)
                e?.let {
                    Log.e("Firestore", "Exception message: ${it.message}")
                }
            }
            .addOnCompleteListener { task ->
                Log.d("Firestore", "Task complete. Successful? ${task.isSuccessful}")
            }
    }


    // -------------------------
    // Flow for Device Location Updates
    // -------------------------
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
