package com.example.fridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridge.data.model.FoodItem
import com.example.fridge.ui.components.FoodIcon
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OverviewTab(
    foodList: List<FoodItem>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val currentTime = System.currentTimeMillis()
    val twoDaysInMillis = 1L * 24 * 60 * 60 * 1000
    val totalItems = foodList.size
    val expiredItems = foodList.filter { it.expiry < currentTime - (1L * 24 * 60 * 60 * 1000) }
    val expiringSoonItems = foodList.filter { it.expiry in currentTime - (1L * 24 * 60 * 60 * 1000)..(currentTime + twoDaysInMillis) }
    val safeCount = totalItems - expiredItems.size - expiringSoonItems.size

    val urgentItems = (expiredItems + expiringSoonItems).sortedBy { it.expiry }


    val sdf = SimpleDateFormat("EEEE, dd 'thg' MM", Locale("vi", "VN"))
    val todayString = sdf.format(Date()).replaceFirstChar { it.uppercase() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 110.dp)
    ) {
        Text(
            text = todayString,
            fontSize = 14.sp,
            color = Color(0xFFA0A5BA),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard(
                title = "Tổng số món",
                value = totalItems.toString(),
                iconColor = Color(0xFF00BFA5),
                bgColor = Color(0xFFE0F2F1),
                icon = Icons.Outlined.Inventory2,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Đang tươi ngon",
                value = safeCount.toString(),
                iconColor = Color(0xFF4CAF50),
                bgColor = Color(0xFFE8F5E9),
                icon = Icons.Outlined.AutoAwesome,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard(
                title = "Sắp hết hạn",
                value = expiringSoonItems.size.toString(),
                iconColor = Color(0xFFFF9500),
                bgColor = Color(0xFFFFF3E0),
                icon = Icons.Outlined.WarningAmber,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Đã quá hạn",
                value = expiredItems.size.toString(),
                iconColor = Color(0xFFFF3B30),
                bgColor = Color(0xFFFFEBEE),
                icon = Icons.Outlined.ErrorOutline,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (urgentItems.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cần xử lý ngay",
                    fontSize = 20.sp,
                    color = Color(0xFF2C3241),
                    fontWeight = FontWeight.ExtraBold
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFFF3B30).copy(alpha = 0.1f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${urgentItems.size} món",
                        color = Color(0xFFFF3B30),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                urgentItems.forEach { food ->
                    MiniUrgentCard(food = food, currentTime = currentTime)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF7F8FA))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.AutoAwesome, null, tint = Color(0xFF00BFA5), modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Tuyệt vời!", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C3241))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Không có món nào sắp hỏng cả.", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}


@Composable
fun StatCard(title: String, value: String, iconColor: Color, bgColor: Color, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.height(90.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(text = value, fontSize = 26.sp, fontWeight = FontWeight.Black, color = iconColor)
            }

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = iconColor.copy(alpha = 0.8f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun MiniUrgentCard(food: FoodItem, currentTime: Long) {
    val diffMillis = food.expiry - currentTime + (24 * 60 * 60 * 1000)
    val daysLeft = (diffMillis / (24 * 60 * 60 * 1000)).toInt()

    val isExpired = diffMillis < 0
    val statusColor = if (isExpired) Color(0xFFFF3B30) else Color(0xFFFF9500)
    val statusIcon = if (isExpired) Icons.Outlined.ErrorOutline else Icons.Outlined.WarningAmber


    val statusText = when {
        isExpired -> "Đã quá hạn!"
        daysLeft == 0 -> "Hết hạn hôm nay"
        else -> "Còn $daysLeft ngày"
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(statusColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                FoodIcon(iconKey = food.image, modifier = Modifier.size(40.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3241),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = statusText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = statusColor
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(statusColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = statusText,
                    tint = statusColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}