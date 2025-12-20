package com.example.mobileapp.data.model

import android.net.Uri

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val photoUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
