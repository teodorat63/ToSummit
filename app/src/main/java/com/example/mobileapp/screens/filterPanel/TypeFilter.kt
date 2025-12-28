package com.example.mobileapp.screens.filterPanel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mobileapp.data.model.LocationType


@Composable
fun TypeFilterSection(
    currentType: LocationType?,
    onTypeChange: (LocationType?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text("Type", style = MaterialTheme.typography.labelMedium)

    TextButton(
        onClick = { expanded = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            currentType
                ?.name
                ?.lowercase()
                ?.replaceFirstChar { it.uppercase() }
                ?: "All"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("All") },
            onClick = {
                onTypeChange(null)
                expanded = false
            }
        )

        LocationType.entries.forEach { type ->
            DropdownMenuItem(
                text = { Text(type.name) },
                onClick = {
                    onTypeChange(type)
                    expanded = false
                }
            )
        }
    }
}
