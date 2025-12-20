package com.example.mobileapp.screens.Location

import android.location.Location
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.data.model.LocationType
import com.example.mobileapp.data.remote.cloudinary.CloudinaryDataSource
import com.example.mobileapp.data.repository.AuthRepository
import com.example.mobileapp.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import com.google.firebase.Timestamp


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val cloudinaryDataSource: CloudinaryDataSource,
    private val authRepository: AuthRepository


) : ViewModel() {

    val locationObjects = repository.locationObjects

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    // Dialog & input state
    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _nameInput = MutableStateFlow("")
    val nameInput: StateFlow<String> = _nameInput

    private val _descriptionInput = MutableStateFlow("")
    val descriptionInput: StateFlow<String> = _descriptionInput

    private val _typeInput = MutableStateFlow(LocationType.OTHER)
    val typeInput: StateFlow<LocationType> = _typeInput

    private val _selectedObject = MutableStateFlow<LocationObject?>(null)
    val selectedObject: StateFlow<LocationObject?> = _selectedObject

    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri: StateFlow<Uri?> = _photoUri

    init {
        viewModelScope.launch {
            repository.getLocationUpdates().collect {
                _location.value = it
            }
        }
    }

    fun showDialog() { _isDialogVisible.value = true }
    fun hideDialog() { _isDialogVisible.value = false }

    fun onNameChange(newValue: String) { _nameInput.value = newValue }
    fun onDescriptionChange(newValue: String) { _descriptionInput.value = newValue }

    fun onTypeChange(newType: LocationType) {
        _typeInput.value = newType
    }

    fun onMarkerClick(obj: LocationObject) {
        _selectedObject.value = obj
    }

    fun onPhotoSelected(uri: Uri?) {
        _photoUri.value = uri

    }

    fun clearSelectedObject() {
        _selectedObject.value = null
    }

    fun addLocationObject() {
        val loc = _location.value ?: return
        val name = _nameInput.value
        if (name.isBlank()) return

        viewModelScope.launch {

            val locationId = UUID.randomUUID().toString()

            val imageUrl = _photoUri.value?.let { uri ->
                cloudinaryDataSource.uploadLocationImage(uri, locationId)
            } ?: ""

            // Get current user
            val currentUser = authRepository.currentUser
            val userName = currentUser?.email ?: "Anonymous"

            val newObject = LocationObject(
                id = locationId,
                type = _typeInput.value,
                latitude = loc.latitude,
                longitude = loc.longitude,
                title = name,
                description = _descriptionInput.value,
                authorName = userName,
                createdAt = Timestamp.now(),
                photoUrl = imageUrl
            )

            repository.addLocationObject(newObject)

            // Reset state
            _nameInput.value = ""
            _descriptionInput.value = ""
            _photoUri.value = null
            _typeInput.value = LocationType.OTHER
            _isDialogVisible.value = false
        }
    }


}

