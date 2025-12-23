package com.example.mobileapp.screens.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun LeaderboardScreen(viewModel: LeaderboardViewModel = hiltViewModel()) {
    val users by viewModel.users.collectAsState()
    val currentUserId = viewModel.currentUserId

    Column(modifier = Modifier.fillMaxSize()) {
        LeaderboardHeader(users.size)
        TopThreePodium(users)
        LeaderboardList(users, currentUserId)
    }
}


