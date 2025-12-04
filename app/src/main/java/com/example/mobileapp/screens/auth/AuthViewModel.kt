package com.example.mobileapp.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        viewModelScope.launch {
            val result = repository.login(email, password)
            if (result.isFailure) {
                errorMessage = result.exceptionOrNull()?.localizedMessage
            } else {
                errorMessage = null
                // TODO: Navigate to next screen
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            val result = repository.register(email, password)
            if (result.isFailure) {
                errorMessage = result.exceptionOrNull()?.localizedMessage
            } else {
                errorMessage = null
                // TODO: Navigate to next screen
            }
        }
    }

    fun logout() {
        repository.logout()
    }

//    val currentUser: FirebaseUser?
//        get() = repository.currentUser
}

