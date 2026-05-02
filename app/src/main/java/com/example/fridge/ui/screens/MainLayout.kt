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
import androidx.compose.material3.FabPosition
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fridge.R
import com.example.fridge.ui.components.BottomNavTab
import com.example.fridge.ui.components.BottomNavigationBar
import com.example.fridge.ui.components.NotificationBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    onFabClick: () -> Unit,
    onTabSelected: (BottomNavTab) -> Unit,
    currentTab: BottomNavTab,
    unreadCount: Int,
    onNotificationClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    ) {
    val headerInfo = when(currentTab) {
        BottomNavTab.Overview -> "Tổng Quan" to "Đồ ăn hôm nay"
        BottomNavTab.Fridge -> "Quản Lý" to "Tủ lạnh của bạn"
        BottomNavTab.Suggest -> "Gợi Ý" to "Món ăn thông minh"
    }

    Scaffold(
        topBar = {
            Header(
                title = headerInfo.first,
                subtitle = headerInfo.second,
                unreadCount = unreadCount,
                onNotificationClick = onNotificationClick
            )
        },

        floatingActionButton = {
            if (currentTab == BottomNavTab.Fridge) {
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

        floatingActionButtonPosition = FabPosition.End,

        bottomBar = {
            BottomNavigationBar(
                currentTab = currentTab,
                onTabSelected = onTabSelected
            )
        }

    ) { paddingValues ->
        Box(modifier = Modifier.background(Color(0xFFFBFBF6))) {
            content(paddingValues)
        }
    }
}
