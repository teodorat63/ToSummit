package com.example.mobileapp.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobileapp.data.model.User


@Composable
fun LeaderboardList(
    users: List<User>,
    currentUserId: String?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(users) { index, user ->
            LeaderboardRow(
                rank = index + 1,
                user = user,
                isCurrentUser = user.uid == currentUserId
            )
        }
    }
}

@Composable
fun LeaderboardRow(
    rank: Int,
    user: User,
    isCurrentUser: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentUser)
                Color(0xFFE6FFFA)
            else
                Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Rank
            Text(
                text = rank.toString(),
                modifier = Modifier.width(32.dp)
            )

            // Avatar
            AsyncImage(
                model = user.photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            // Name
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${user.firstName} ${user.lastName}")
                    if (isCurrentUser) {
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "You",
                            color = Color(0xFF059669),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Points
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${user.points}",
                    color = Color(0xFF059669),
                    fontWeight = FontWeight.Bold
                )
                Text("points", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

