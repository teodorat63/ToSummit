package com.example.mobileapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobileapp.ui.theme.AppShapes
import com.example.mobileapp.ui.theme.Emerald600

@Composable
fun AppOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = AppShapes.medium,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Emerald600
        ),
        border = BorderStroke(2.dp, Emerald600)
    ) {
        Text(text)
    }
}


@Preview(showBackground = true)
@Composable
fun AppOutlinedButtonPreview() {
    MaterialTheme {
        AppOutlinedButton(
            text = "Create Account",
            onClick = {}
        )
    }
}
