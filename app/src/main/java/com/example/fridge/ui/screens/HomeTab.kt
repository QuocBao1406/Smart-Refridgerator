package com.example.fridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridge.R
import com.example.fridge.data.model.FoodItem
import com.example.fridge.ui.components.FoodItemCard

@Composable
fun HomeTab(
    foodList: List<FoodItem>,
    onDeleteFood: (FoodItem) -> Unit,
    onCardClick: (FoodItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tất cả") }

    val categories = listOf("Tất cả", "Thịt & Cá", "Rau củ", "Trái cây", "Trứng", "Đồ uống", "Khác")

    
    val filteredList = foodList.filter { food ->
        val matchesSearch = food.name.contains(searchQuery, ignoreCase = true)
        val matchesCategory = if (selectedCategory == "Tất cả") true else food.type == selectedCategory
        matchesSearch && matchesCategory
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF3FDFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3FDFF))
                .padding(top = 12.dp, bottom = 8.dp)
        ) {
            PremiumSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category
                    Surface(
                        onClick = { selectedCategory = category },
                        shape = CircleShape,
                        color = if (isSelected) Color(0xFF1C1C1E) else Color(0xFFF2F2F7),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF8E8E93)
                            )
                        }
                    }
                }
            }
        }

        if (filteredList.isEmpty()) {
            EmptyStateDisplay(isSearchEmpty = searchQuery.isNotEmpty())
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp) 
            ) {
                items(filteredList, key = { it.id }) { food -> 
                    FoodItemCard(
                        food = food,
                        onDeleteFood = onDeleteFood,
                        onCardClick = onCardClick
                    )
                }
            }
        }
    }
}



@Composable
fun PremiumSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle = TextStyle(color = Color(0xFF1C1C1E), fontSize = 16.sp),
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFFF2F2F7), CircleShape) 
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Search, null, tint = Color(0xFF8E8E93), modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text("Tìm kiếm đồ ăn...", color = Color(0xFF8E8E93), fontSize = 16.sp)
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Outlined.Close, null, tint = Color(0xFF8E8E93), modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    )
}

@Composable
fun EmptyStateDisplay(isSearchEmpty: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(bottom = 64.dp), 
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.empty_box),
                contentDescription = "Empty",
                tint = Color.Unspecified,
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (isSearchEmpty) "Không tìm thấy món nào!" else "Tủ lạnh đang trống",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1E)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isSearchEmpty) "Thử dùng từ khóa khác hoặc kiểm tra lại chính tả nhé." else "Hãy bấm nút '+' để bắt đầu quản lý thực phẩm của bạn.",
            fontSize = 14.sp,
            color = Color(0xFF8E8E93),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}