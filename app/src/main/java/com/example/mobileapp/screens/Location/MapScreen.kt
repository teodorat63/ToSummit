package com.example.mobileapp.screens.Location

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobileapp.screens.Location.filters.FilteredLocationList
import com.example.mobileapp.screens.Location.dialogs.AddLocationFullScreenDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(viewModel: LocationViewModel = hiltViewModel()) {
    val location by viewModel.location.collectAsState()
    val isDialogVisible by viewModel.isDialogVisible.collectAsState()
    val nameInput by viewModel.nameInput.collectAsState()
    val descriptionInput by viewModel.descriptionInput.collectAsState()
    val typeInput by viewModel.typeInput.collectAsState()
    val selectedObject by viewModel.selectedObject.collectAsState()
    val photoUri by viewModel.photoUri.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val locationObjects by viewModel.filteredLocationObjects.collectAsState()
    val isListVisible by viewModel.isListVisible.collectAsState()




    val cameraPositionState = rememberCameraPositionState()
    var hasCentered by remember { mutableStateOf(false) }
    var isFilterVisible by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onPhotoSelected(uri)
    }

    LaunchedEffect(location) {
        location?.let { loc ->
            if (!hasCentered) {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(loc.latitude, loc.longitude), 17f
                    )
                )
                hasCentered = true
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
    Box(Modifier.weight(1f)) {
        MapContent(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            location = location,
            locationObjects = locationObjects,
            selectedObject = selectedObject,
            onMarkerClick = viewModel::onMarkerClick,
            onDismissDetails = viewModel::clearSelectedObject
        )
        MapActions(
            onAddClick = viewModel::showDialog,
            onFilterClick = { isFilterVisible = !isFilterVisible },
            onListClick =  viewModel::toggleListVisibility
        )
    }
    if (isDialogVisible) {
        AddLocationFullScreenDialog(
            name = nameInput,
            description = descriptionInput,
            type = typeInput,
            photoUri = photoUri,
            onPickPhoto = { galleryLauncher.launch("image/*") },
            onTypeChange = viewModel::onTypeChange,
            onNameChange = viewModel::onNameChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onDismiss = viewModel::hideDialog,
            onConfirm = viewModel::addLocationObject
        )

    }


    }

    if (isFilterVisible) {
        FilterPanel(
            currentType = filter.type,
            currentAuthor = filter.authorName,
            currentStartDate = filter.dateRange?.first,
            currentEndDate = filter.dateRange?.second,
            onTypeChange = viewModel::setTypeFilter,
            onAuthorChange = viewModel::setAuthorFilter,
            onDateChange = { start, end ->
                if (start != null && end != null) viewModel.setDateFilter(start, end)
            },
            onClear = {
                viewModel.clearFilters()
                isFilterVisible = false
            }
        )

    }

    if (isListVisible) { // Observed from ViewModel
        FilteredLocationList(
            locations = locationObjects,
            onItemClick = viewModel::onMarkerClick,
            onClose =  viewModel::toggleListVisibility
        )
    }
}
