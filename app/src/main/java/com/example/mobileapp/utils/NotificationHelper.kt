package com.example.mobileapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationObject

object NotificationHelper {

    const val TRACKING_CHANNEL = "tracking_channel"

    const val ALERTS_CHANNEL = "alerts_channel"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val tracking = NotificationChannel(
                TRACKING_CHANNEL,
                "Location Tracking",
                NotificationManager.IMPORTANCE_LOW
            )

            val alerts = NotificationChannel(
                ALERTS_CHANNEL,
                "Nearby Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(tracking)
            manager.createNotificationChannel(alerts)
        }
    }

    fun trackingNotification(context: Context): Notification =
        NotificationCompat.Builder(context, "tracking_channel")
            .setContentTitle("ToSummit")
            .setContentText("Tracking nearby locations")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()


    fun nearbyNotification(
        context: Context,
        location: LocationObject,
        distance: Float
    ): Notification {


        return NotificationCompat.Builder(context, ALERTS_CHANNEL)
            .setContentTitle("Nearby location")
            .setContentText("${location.title} is ${distance}m away")
            .setSmallIcon(R.drawable.ic_nearme)
            .build()
    }
}
