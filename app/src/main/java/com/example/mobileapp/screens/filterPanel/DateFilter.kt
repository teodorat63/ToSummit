package com.example.mobileapp.screens.filterPanel

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.components.DatePickerField
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun DateFilterSection(
    currentStartDate: Long?,
    currentEndDate: Long?,
    dateFormat: SimpleDateFormat,
    onDateChange: (Long?, Long?) -> Unit
) {
    val context = LocalContext.current
    var startDate by remember { mutableStateOf(currentStartDate) }
    var endDate by remember { mutableStateOf(currentEndDate) }

    val startText = startDate?.let { dateFormat.format(Date(it)) }.orEmpty()
    val endText = endDate?.let { dateFormat.format(Date(it)) }.orEmpty()

    DatePickerField(
        label = "Start Date",
        value = startText,
        initialDate = currentStartDate,
        onDateSelected = {
            startDate = it
            onDateChange(startDate, endDate)
        },
        context = context
    )

    Spacer(Modifier.height(12.dp))

    DatePickerField(
        label = "End Date",
        value = endText,
        initialDate = currentEndDate,
        onDateSelected = {
            endDate = it
            onDateChange(startDate, endDate)
        },
        context = context
    )
}
