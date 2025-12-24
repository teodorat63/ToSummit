package com.example.mobileapp.navigation

import androidx.annotation.DrawableRes
import com.example.mobileapp.R
import com.example.mobileapp.screens.Screen

sealed class BottomBarScreen(
    val screen: Screen,
    val title: String,
    @DrawableRes val iconRes: Int
) {

    object List : BottomBarScreen(
        screen = Screen.ListScreen,
        title = "List",
        iconRes = R.drawable.ic_list
    )
    object Map : BottomBarScreen(
        screen = Screen.MapScreen,
        title = "Map",
        iconRes = R.drawable.ic_map
    )

    object Leaderboard : BottomBarScreen(
        screen = Screen.LeaderboardScreen,
        title = "Leaderboard",
        iconRes = R.drawable.ic_trophy
    )
}
