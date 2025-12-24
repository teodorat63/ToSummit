package com.example.mobileapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomBarScreen.List,
        BottomBarScreen.Map,
        BottomBarScreen.Leaderboard
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            launchSingleTop = true      // Avoid creating multiple instances
                            restoreState = true         // Restore previous state (ViewModel + UI)
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true       // Save state of other tabs
                            }
                        }
                    }
                },
                {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()

    BottomBar(navController = navController)
}

