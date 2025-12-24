package com.example.mobileapp.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mobileapp.ui.theme.Emerald600
import com.example.mobileapp.ui.theme.Emerald700
import com.example.mobileapp.ui.theme.White

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Emerald600,
            disabledContainerColor = Emerald700,
            contentColor = White
        )
    ) {
        Text(text)
    }
}