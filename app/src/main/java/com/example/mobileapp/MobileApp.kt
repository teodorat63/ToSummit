package com.example.mobileapp

import android.app.Application
import com.example.mobileapp.utils.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobileApp : Application() {
override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannels(this)
    }
}