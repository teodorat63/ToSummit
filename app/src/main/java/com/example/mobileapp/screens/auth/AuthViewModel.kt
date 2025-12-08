package com.example.mobileapp.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    var loginSuccess by mutableStateOf(false)
        private set

    var registerSuccess by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) { email = newEmail }
    fun onPasswordChange(newPassword: String) { password = newPassword }

    fun login() {
        viewModelScope.launch {
            val result = repository.login(email, password)
            if (result.isFailure) {
                errorMessage = result.exceptionOrNull()?.localizedMessage
                loginSuccess = false
            } else {
                errorMessage = null
                loginSuccess = true
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            val result = repository.register(email, password)
            if (result.isFailure) {
                errorMessage = result.exceptionOrNull()?.localizedMessage
                registerSuccess=false
            } else {
                errorMessage = null
                registerSuccess=true
            }
        }
    }

    fun logout() {
        repository.logout()
        loginSuccess = false
    }



//    val currentUser: FirebaseUser?
//        get() = repository.currentUser
}

