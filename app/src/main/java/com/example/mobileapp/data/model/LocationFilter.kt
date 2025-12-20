package com.example.mobileapp.data.model


data class LocationFilter(
    val type: LocationType? = null,
    val authorName: String? = null,
    val dateRange: Pair<Long, Long>? = null
) {
    fun isEmpty(): Boolean = type == null && authorName.isNullOrBlank() && dateRange == null
}