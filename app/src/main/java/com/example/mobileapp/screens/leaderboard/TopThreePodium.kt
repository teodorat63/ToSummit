package com.example.mobileapp.screens.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobileapp.R
import com.example.mobileapp.data.model.User

@Composable
fun TopThreePodium(users: List<User>) {
    if (users.size < 3) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        PodiumUser(user = users[1], rank = 2, size = 64.dp)
        PodiumUser(user = users[0], rank = 1, size = 72.dp)
        PodiumUser(user = users[2], rank = 3, size = 56.dp)
    }

}

@Composable
fun PodiumUser(
    user: User,
    rank: Int,
    size: Dp,
) {
    val borderColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.LightGray
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        AsyncImage(
            model = user.photoUrl,
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(
                    width = 4.dp,
                    color = borderColor,
                    shape = CircleShape
                ),
            contentScale = ContentScale.Crop
        )

        if (rank in 1..3) {
            Image(
                painter = painterResource(id = R.drawable.medal),
                contentDescription = null,
                colorFilter = ColorFilter.tint(borderColor),
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            user.firstName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text("${user.points} pts", color = Color(0xFF059669))
    }
}