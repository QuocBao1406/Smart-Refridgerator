package com.example.fridge.ui.screens

import Header
import android.R.attr.title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fridge.R
import com.example.fridge.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    onFabClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    currentTab: Int,
    content: @Composable (PaddingValues) -> Unit
) {
    val headerInfo = when(currentTab) {
        0 -> "Quản Lý" to "Tủ lạnh của bạn"
        1 -> "Báo Cáo" to "Thống kê tiêu thụ"
        else -> "Cài đặt" to "Cài đặt ứng dụng"
    }

    Scaffold(
        topBar = {
            Header(
                title = headerInfo.first,
                subtitle = headerInfo.second
            )
        },

        floatingActionButton = {
            if (currentTab == 0) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF00BFA5), Color(0xFF009688))))
                        .clickable { onFabClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, tint = Color.White)
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
        Box(modifier = Modifier.background(Color(0xFFFBFBF6))) {
            content(paddingValues)
        }
    }
}