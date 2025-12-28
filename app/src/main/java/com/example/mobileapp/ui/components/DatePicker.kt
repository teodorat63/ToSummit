package com.example.mobileapp.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.Calendar

@Composable
fun DatePickerField(
    label: String,
    value: String,
    initialDate: Long?,
    onDateSelected: (Long) -> Unit,
    context: android.content.Context
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = {
                val calendar = Calendar.getInstance()
                initialDate?.let { calendar.timeInMillis = it }

                DatePickerDialog(
                    context,
                    { _: DatePicker, year, month, day ->
                        calendar.set(year, month, day)
                        onDateSelected(calendar.timeInMillis)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Icon(Icons.Default.DateRange, contentDescription = "Pick $label")
            }
        }
    )
}
