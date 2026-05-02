package com.example.fridge.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridge.R
import com.example.fridge.data.model.FoodItem
import com.example.fridge.utils.toFormattedDate
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCard(
    food: FoodItem,
    onDeleteFood: (FoodItem) -> Unit,
    onCardClick: (FoodItem) -> Unit,
    modifier: Modifier = Modifier
) {
    
    val dismissState = rememberSwipeToDismissBoxState(
        
        confirmValueChange = { true },
        positionalThreshold = { distance -> distance * 0.4f }
    )

    
    var isFingerDown by remember { mutableStateOf(false) }

    LaunchedEffect(dismissState.currentValue, isFingerDown) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && !isFingerDown) {
            delay(150)
            onDeleteFood(food)
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        modifier = modifier
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        isFingerDown = event.changes.any { it.pressed }
                    }
                }
            },
        state = dismissState,
        enableDismissFromStartToEnd = false,

        
        backgroundContent = {
            val bgColor by animateColorAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart)
                    Color(0xFFFF3B30) else Color.Transparent,
                label = "bg_color"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(bgColor)
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Outlined.DeleteOutline, null, tint = Color.White, modifier = Modifier.size(32.dp))
            }
        },

        
        content = {
            val currentTime = System.currentTimeMillis()
            val isExpired = food.expiry < currentTime - (1L * 24 * 60 * 60 * 1000)
            val isExpiringSoon = food.expiry in currentTime - (1L * 24 * 60 * 60 * 1000)..(currentTime + (2L * 24 * 60 * 60 * 1000))

            val statusColor = when {
                isExpired -> Color(0xFFFF3B30)
                isExpiringSoon -> Color(0xFFFF9500)
                else -> Color(0xFF00BFA5)
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCardClick(food) },
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(statusColor.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        FoodIcon(
                            iconKey = food.image,
                            modifier = Modifier.size(36.dp),
                            contentDescription = food.name
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = food.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2C3241),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${food.quantity} ${food.unit}  •  ",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFA0A5BA)
                            )
                            Text(
                                text = if (isExpired) "Đã hết hạn" else food.expiry.toFormattedDate(),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF7F8FA))
                            .clickable { onDeleteFood(food) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "Xóa nhanh",
                            tint = Color(0xFFFF8980),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    )
}