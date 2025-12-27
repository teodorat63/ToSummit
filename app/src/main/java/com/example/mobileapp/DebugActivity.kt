package com.example.mobileapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.data.model.LocationType
import com.example.mobileapp.data.model.User
import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.data.repository.LocationRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DebugActivity : AppCompatActivity() {
    private val firestore = FirebaseFirestore.getInstance()
    @Inject lateinit var authRepository: AuthRepository
    @Inject lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Now you can directly call registerMultipleUsers()
        addAllSampleLocations()
    }

    private fun registerMultipleUsers() {
        val users = listOf(
            User(
                email = "marko.petrovic@example.com",
                firstName = "Marko",
                lastName = "Petrović",
                phone = "+381601234567",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523857/download_13_djpgco.jpg"
            ),
            User(
                email = "ana.jovanovic@example.com",
                firstName = "Ana",
                lastName = "Jovanović",
                phone = "+381611234568",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523792/download_9_vajsud.jpg"
            ),
            User(
                email = "nikola.markovic@example.com",
                firstName = "Nikola",
                lastName = "Marković",
                phone = "+381621234569",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523856/download_12_cl3vlu.jpg"
            ),
            User(
                email = "jelena.stojanovic@example.com",
                firstName = "Jelena",
                lastName = "Stojanović",
                phone = "+381631234570",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523682/download_8_m88ej8.jpg"
            ),
            User(
                email = "milos.ivanovic@example.com",
                firstName = "Miloš",
                lastName = "Ivanović",
                phone = "+381641234571",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523682/download_7_mz5usn.jpg"
            ),
            User(
                email = "sofija.kovacevic@example.com",
                firstName = "Sofija",
                lastName = "Kovačević",
                phone = "+381651234572",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523681/download_5_kiw9ep.jpg"
            ),
            User(
                email = "marija.milic@example.com",
                firstName = "Marija",
                lastName = "Milić",
                phone = "+381671234574",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523680/download_2_zobro1.jpg"
            ),
            User(
                email = "filip.nikolic@example.com",
                firstName = "Filip",
                lastName = "Nikolić",
                phone = "+381681234575",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766523682/download_6_fkvnmh.jpg"
            )
        )


        CoroutineScope(Dispatchers.IO).launch {
            users.forEach { user ->
                val password = "password123"
                val result = authRepository.register(user, password, null)
                if (result.isSuccess) {
                    println("Registered ${user.email}")
                } else {
                    println("Failed to register ${user.email}: ${result.exceptionOrNull()}")
                }
            }
        }
    }

    fun addAllSampleLocations() {
        val sampleLocations = listOf(
            LocationObject(
                type = LocationType.SUMMIT,
                latitude = 43.7744, // Rtanj Peak (Šiljak)
                longitude = 21.8942,
                title = "Rtanj Peak",
                description = "Famous pyramid-shaped mountain known for its scenic views and hiking trails.",
                authorName = "marko.petrovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766581767/unnamed_t18tmd.webp"
            ),
            LocationObject(
                type = LocationType.SHELTER,
                latitude = 43.9100, // Mitrovac na Tari
                longitude = 19.4180,
                title = "Tara Mountain Hut",
                description = "Cozy mountain shelter with basic amenities for hikers in Tara National Park.",
                authorName = "jelena.stojanovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766581773/guesthouse-planinarski_cjug0w.jpg"
            ),
            LocationObject(
                type = LocationType.WATER,
                latitude = 43.9593, // Vrelo River (Spring of the Drina)
                longitude = 19.4116,
                title = "Drina River Spring",
                description = "Crystal-clear spring of the Vrelo River flowing into the Drina, popular among hikers.",
                authorName = "nikola.markovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766581886/b8520fac-1973-4908-81e4-d47494b9ea09_national-park-tara-house-on-drina-river-banjska-stena-view-point-wooden-town_xfgjx2.png"
            ),
            LocationObject(
                type = LocationType.VIEWPOINT,
                latitude = 43.3609, // Molitva Viewpoint (Uvac)
                longitude = 19.9575,
                title = "Uvac Canyon Viewpoint",
                description = "Stunning viewpoint overlooking the meandering Uvac River and its famous vultures.",
                authorName = "ana.jovanovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766581953/Uvac-river-cruising-bigtours-1024x577_sdbbhr.webp"
            ),
            LocationObject(
                type = LocationType.PARKING,
                latitude = 43.7262, // Zlatibor Center
                longitude = 19.6988,
                title = "Zlatibor Trail Parking",
                description = "Parking lot for hikers starting popular trails around Zlatibor Mountain.",
                authorName = "filip.nikolic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766581985/tablet_6131616668600465_w8s6oh.jpg"
            ),
            LocationObject(
                type = LocationType.SUMMIT,
                latitude = 43.3937, // Midžor Peak (Stara Planina)
                longitude = 22.6784,
                title = "Stara Planina Peak",
                description = "Highest peak in Stara Planina range, offering panoramic views of eastern Serbia.",
                authorName = "marija.milic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766582034/DSCN0018_sfbkwf.jpg"
            ),
            LocationObject(
                type = LocationType.SHELTER,
                latitude = 43.9511, // Near Banjska Stena, Tara
                longitude = 19.4016,
                title = "Tara Forest Shelter",
                description = "Small wooden shelter for hikers exploring dense Tara forests.",
                authorName = "milos.ivanovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766582122/549456266_ah1alr.jpg"
            ),
            LocationObject(
                type = LocationType.WATER,
                latitude = 42.7000, // Vlasina Lake
                longitude = 22.3333,
                title = "Vlasina Lake",
                description = "Artificial lake with a scenic walking trail and opportunities for kayaking.",
                authorName = "sofija.kovacevic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766582161/vlasinsko-jezero_eb1xrc.jpg"
            ),
            LocationObject(
                type = LocationType.VIEWPOINT,
                latitude = 43.4147, // Zlatar Mountain (Babića Brdo)
                longitude = 19.8464,
                title = "Zlatar Lookout",
                description = "Panoramic viewpoint with views over Zlatar Mountain and surrounding valleys.",
                authorName = "marko.petrovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766582198/tablet_53715150473961116_wu1vbs.jpg"
            ),
            LocationObject(
                type = LocationType.OTHER,
                latitude = 44.8162, // Belgrade Center (Republic Square)
                longitude = 20.4605,
                title = "Belgrade Urban Hike Start",
                description = "Starting point for urban hiking routes across Belgrade, connecting city parks and historic spots.",
                authorName = "ana.jovanovic@example.com",
                photoUrl = "https://res.cloudinary.com/dkeqbkfdh/image/upload/v1766582239/3-knez_wdzusi.jpg"
            )

        )

        sampleLocations.forEach { location ->
            val locationWithId = location.copy(id = firestore.collection("location_objects").document().id)
            locationRepository.addLocationObject(locationWithId)
            Log.d("DebugActivity", "Added location: ${location.title} by ${location.authorName}")

        }
    }

}
