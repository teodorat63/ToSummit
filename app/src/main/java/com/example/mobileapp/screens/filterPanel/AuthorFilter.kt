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

@Composable
fun AuthorFilterSection(
    currentAuthor: String?,
    authors: List<String>,
    onAuthorChange: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text("Author", style = MaterialTheme.typography.labelMedium)

    TextButton(
        onClick = { expanded = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(currentAuthor ?: "All")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("All") },
            onClick = {
                onAuthorChange(null)
                expanded = false
            }
        )

        authors.forEach { author ->
            DropdownMenuItem(
                text = { Text(author) },
                onClick = {
                    onAuthorChange(author)
                    expanded = false
                }
            )
        }
    }
}
