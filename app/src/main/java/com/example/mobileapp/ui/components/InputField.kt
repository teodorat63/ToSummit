package com.example.mobileapp.ui.components


import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileapp.ui.theme.AppShapes
import com.example.mobileapp.ui.theme.Emerald600
import com.example.mobileapp.ui.theme.Red600

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        isError = isError,
        modifier = modifier,
        shape = AppShapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Emerald600,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = Red600,

            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,

            focusedLabelColor = Emerald600
        )
    )


}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    InputField(
        value = "",
        onValueChange = {},
        label = "Email",
        placeholder = "Enter your email",
        isError = false
    )
}
