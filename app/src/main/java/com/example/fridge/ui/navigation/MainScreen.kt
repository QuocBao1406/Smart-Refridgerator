package com.example.fridge.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fridge.ui.screens.FridgeScreen
import com.example.fridge.ui.screens.ProfileScreen
import com.example.fridge.ui.screens.RecipeScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Kitchen, contentDescription = "Tủ lạnh") },
                    label = { Text("Tủ lạnh") },
                    selected = currentRoute == "fridge",
                    onClick = {
                        navController.navigate("fridge") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AutoAwesome, contentDescription = "Gợi ý")},
                    label = { Text("Gợi ý") },
                    selected = currentRoute == "recipe",
                    onClick = {
                        navController.navigate("recipe") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = {Icon(Icons.Filled.Person, contentDescription = "Cá nhân")},
                    label = { Text("Cá nhân") },
                    selected = currentRoute == "profile",
                    onClick = {
                        navController.navigate("profile") {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "fridge",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("fridge") { FridgeScreen() }
            composable("recipe") { RecipeScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}