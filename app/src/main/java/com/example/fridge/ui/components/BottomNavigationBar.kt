package com.example.fridge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class BottomNavTab(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Overview: BottomNavTab("overview", "Tổng Quan", Icons.Outlined.GridView)
    object Fridge: BottomNavTab("fridge", "Tủ Lạnh", Icons.Outlined.Kitchen)
    object Suggest: BottomNavTab("suggest", "Gợi Ý", Icons.Outlined.Layers)
}

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onTabSelected: (BottomNavTab) -> Unit
) {
    val tabs = listOf(BottomNavTab.Overview, BottomNavTab.Fridge, BottomNavTab.Suggest)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .shadow(1.dp, RoundedCornerShape(28.dp), ambientColor = Color.Black.copy(alpha = 0.1f))
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                val isSelected = currentRoute == tab.route

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title,
                        tint = if (isSelected) Color(0xFFEF5350) else Color(0xFFD0D5E0),
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = tab.title,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color(0xFF2C3241) else Color(0xFFD0D5E0),
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}