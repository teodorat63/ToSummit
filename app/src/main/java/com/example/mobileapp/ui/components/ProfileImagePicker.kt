package com.example.mobileapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobileapp.R


@Composable
fun ProfileImagePicker(
    imageUri: String?,
    onClickPick: () -> Unit,
    onRemove: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(Color.LightGray, CircleShape)
                .border(width = 3.dp, color = Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize()
                        .clip(CircleShape)
                )

            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )

            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AppOutlinedButton(
            onClick = onClickPick,
            text = if (imageUri == null) "Add Photo" else "Change Photo"
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePickerPreview_NoImage() {
    MaterialTheme {
        ProfileImagePicker(
            imageUri = null,
            onClickPick = {},
            onRemove = null
        )
    }
}