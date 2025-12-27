package com.example.mobileapp.utils

import java.time.Duration
import java.time.Instant
import java.util.Date

fun getRelativeTimeString(date: Date): String {
    val now = Instant.now()
    val created = date.toInstant()
    val duration = Duration.between(created, now)

    return when {
        duration.toMinutes() < 1 -> "Just now"
        duration.toHours() < 1 -> "${duration.toMinutes()} minutes ago"
        duration.toHours() < 24 -> "${duration.toHours()} hours ago"
        duration.toDays() < 7 -> "${duration.toDays()} days ago"
        duration.toDays() < 30 -> "${duration.toDays() / 7} weeks ago"
        duration.toDays() < 365 -> "${duration.toDays() / 30} months ago"
        else -> "${duration.toDays() / 365} years ago"
    }
}
