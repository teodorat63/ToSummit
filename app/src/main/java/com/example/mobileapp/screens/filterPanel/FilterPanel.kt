package com.example.mobileapp.screens.filterPanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileapp.data.model.LocationType
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun FilterPanel(
    currentType: LocationType?,
    currentAuthor: String?,
    currentStartDate: Long?,
    currentEndDate: Long?,
    onTypeChange: (LocationType?) -> Unit,
    authors: List<String>,
    onAuthorChange: (String?) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
    onClear: () -> Unit,
    onClose: () -> Unit
) {
    val dateFormat = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            FilterHeader(onClose)

            Spacer(Modifier.height(12.dp))

            TypeFilterSection(
                currentType = currentType,
                onTypeChange = onTypeChange
            )

            Spacer(Modifier.height(12.dp))

            AuthorFilterSection(
                currentAuthor = currentAuthor,
                authors = authors,
                onAuthorChange = onAuthorChange
            )

            Spacer(Modifier.height(12.dp))

            DateFilterSection(
                currentStartDate = currentStartDate,
                currentEndDate = currentEndDate,
                dateFormat = dateFormat,
                onDateChange = onDateChange
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onClear,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Filters")
            }
        }
    }
}
