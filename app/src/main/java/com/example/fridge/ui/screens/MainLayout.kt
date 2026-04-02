package com.example.fridge.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    title: String,
    onFabClick: () -> Unit,

    currentTab: Int,
    onTabSelected: (Int) -> Unit,

    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },

        floatingActionButton = {
            if(currentTab == 0) {
                FloatingActionButton(onClick = onFabClick) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Thêm")
                }
            }
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Kitchen, contentDescription = "Tủ lạnh") },
                    label = { Text("Tủ lạnh") },
                    selected = currentTab == 0,
                    onClick = { onTabSelected(0) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Analytics, contentDescription = "Thống kê")},
                    label = { Text("Thống kê") },
                    selected = currentTab == 1,
                    onClick = { onTabSelected(1) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Cài đặt")},
                    label = { Text("Cài đặt") },
                    selected = currentTab == 2,
                    onClick = { onTabSelected(2) }
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}