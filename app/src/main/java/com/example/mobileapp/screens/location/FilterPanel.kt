package com.example.mobileapp.screens.location

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobileapp.data.model.LocationType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun FilterPanel(
    currentType: LocationType?,
    currentAuthor: String?,
    currentStartDate: Long?,
    currentEndDate: Long?,
    onTypeChange: (LocationType?) -> Unit,
    onAuthorChange: (String?) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
    onClear: () -> Unit,
    onClose: () -> Unit // ← new parameter for close action
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    var startDate by remember { mutableStateOf(currentStartDate) }
    var endDate by remember { mutableStateOf(currentEndDate) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close filter panel"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Type dropdown
            Text("Type", style = MaterialTheme.typography.labelMedium)
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentType?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "All",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("All") }, onClick = {
                    onTypeChange(null)
                    expanded = false
                })
                LocationType.entries.forEach { type ->
                    DropdownMenuItem(text = { Text(type.name) }, onClick = {
                        onTypeChange(type)
                        expanded = false
                    })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Author input
            OutlinedTextField(
                value = currentAuthor.orEmpty(),
                onValueChange = { onAuthorChange(it.ifBlank { null }) },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Date pickers
            val startText = startDate?.let { dateFormat.format(Date(it)) } ?: ""
            val endText = endDate?.let { dateFormat.format(Date(it)) } ?: ""

            OutlinedTextField(
                value = startText,
                onValueChange = { },
                readOnly = true,
                label = { Text("Start Date") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        val calendar = Calendar.getInstance()
                        currentStartDate?.let { calendar.timeInMillis = it }
                        DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, day: Int ->
                                calendar.set(year, month, day)
                                startDate = calendar.timeInMillis
                                onDateChange(startDate, endDate)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pick start date")
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = endText,
                onValueChange = { },
                readOnly = true,
                label = { Text("End Date") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        val calendar = Calendar.getInstance()
                        currentEndDate?.let { calendar.timeInMillis = it }
                        DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, day: Int ->
                                calendar.set(year, month, day)
                                endDate = calendar.timeInMillis
                                onDateChange(startDate, endDate)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pick end date")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    startDate = null
                    endDate = null
                    onClear()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Clear Filters", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}


