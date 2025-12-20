package com.example.mobileapp.data.model

import com.google.firebase.Timestamp

data class User(
    val uid: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val photoUrl: String? = null,
    val points: Int = 0,
    val createdAt: Timestamp = Timestamp.now()
)