package com.example.mobileapp.screens.auth

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// ---------------------
// LOGIN UI STATE
// ---------------------
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

// ---------------------
// REGISTER UI STATE
// ---------------------
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

// ---------------------
// VIEWMODEL
// ---------------------
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // UI State for Login Screen
    var loginState by mutableStateOf(LoginUiState())
        private set

    // UI State for Register Screen
    var registerState by mutableStateOf(RegisterUiState())
        private set


    // ------------------------------------
    // LOGIN STATE UPDATERS
    // ------------------------------------
    fun onLoginEmailChange(value: String) {
        loginState = loginState.copy(email = value)
    }

    fun onLoginPasswordChange(value: String) {
        loginState = loginState.copy(password = value)
    }


    // ------------------------------------
    // REGISTER STATE UPDATERS
    // ------------------------------------
    fun onRegisterEmailChange(value: String) {
        registerState = registerState.copy(email = value)
    }

    fun onRegisterPasswordChange(value: String) {
        registerState = registerState.copy(password = value)
    }

    fun onRegisterFirstNameChange(value: String) {
        registerState = registerState.copy(firstName = value)
    }

    fun onRegisterLastNameChange(value: String) {
        registerState = registerState.copy(lastName = value)
    }

    fun onRegisterPhoneChange(value: String) {
        registerState = registerState.copy(phone = value)
    }

    fun onRegisterPhotoChange(uri: Uri?) {
        registerState = registerState.copy(photoUri = uri)
    }


    // ------------------------------------
    // LOGIN LOGIC
    // ------------------------------------
    fun login() {
        loginState = loginState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = repository.login(loginState.email, loginState.password)

            loginState = if (result.isSuccess) {
                loginState.copy(
                    isLoading = false,
                    success = true,
                    error = null
                )
            } else {
                loginState.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.localizedMessage
                )
            }
        }
    }

    // ------------------------------------
    // REGISTER LOGIC
    // ------------------------------------
    fun register() {
        registerState = registerState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = repository.register(
                email = registerState.email,
                password = registerState.password,
                firstName = registerState.firstName,
                lastName = registerState.lastName,
                phone = registerState.phone,
                photoUri = registerState.photoUri
            )

            registerState = if (result.isSuccess) {
                registerState.copy(
                    isLoading = false,
                    success = true,
                    error = null
                )
            } else {
                registerState.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.localizedMessage
                )
            }
        }
    }


    // ------------------------------------
    // LOGOUT
    // ------------------------------------
    fun logout() {
        repository.logout()
        loginState = LoginUiState()     // reset login data
        registerState = RegisterUiState() // reset register data
    }
}
