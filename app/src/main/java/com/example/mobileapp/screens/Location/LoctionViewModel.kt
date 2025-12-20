package com.example.mobileapp.screens.Location

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.model.LocationObject
import com.example.mobileapp.data.model.LocationType
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
    private val repository: LocationRepository
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

    fun addLocationObject() {
        val loc = _location.value ?: return
        val name = _nameInput.value
        if (name.isBlank()) return

        val newObject = LocationObject(
            id = UUID.randomUUID().toString(),
            type = _typeInput.value,
            latitude = loc.latitude,
            longitude = loc.longitude,
            title = name,
            description = _descriptionInput.value,
            authorId = "anonymous", // 🔹 replace later with FirebaseAuth UID
            createdAt = Timestamp.now(),
            photoUrl = ""
        )

        repository.addLocationObject(newObject)

        // Reset inputs
        _nameInput.value = ""
        _descriptionInput.value = ""
        _typeInput.value = LocationType.OTHER
        _isDialogVisible.value = false
    }

}

