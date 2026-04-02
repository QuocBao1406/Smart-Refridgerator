package com.example.fridge.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fridge.data.model.FoodItem
import com.example.fridge.ui.components.FoodItemCard

@Composable
fun HomeTab(
    foodList: List<FoodItem>,
    onDeleteFood: (FoodItem) -> Unit,
    onFoodClick: (FoodItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = foodList.filter { food ->
        food.name.contains(searchQuery, ignoreCase = true)
    }

    val sortedFoodList = foodList.sortedBy { it.expiry }
    val currentTime = System.currentTimeMillis()
    val twoDaysInMillis = 2L * 24 * 60 * 60 * 1000

    val expiredList = sortedFoodList.filter { it. expiry < currentTime }

    val expiringSoonList = sortedFoodList.filter {
        it.expiry >= currentTime && it.expiry <= (currentTime + twoDaysInMillis)
    }

    val normalList = sortedFoodList.filter {
        it.expiry > (currentTime + twoDaysInMillis)
    }

    Column (modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Tìm kiếm đồ ăn...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Tìm") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = ""}) {
                        Icon(Icons.Filled.Clear, contentDescription = "Xóa")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            //expiredList
            if (expiredList.isNotEmpty()) {
                item {
                    Text(
                        text = "Đã hết hạn",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                    )
                }
                items(
                    items = expiredList,
                    key = { it.id }
                ) { food ->
                    FoodItemCard(
                        food = food,
                        onDeleteClick = { onDeleteFood(it) },
                        onCardClick = { onFoodClick(it) }
                    )
                }
            }

            if (expiringSoonList.isNotEmpty()) {
                item {
                    Text(
                        text = "Sắp hết hạn",
                        color = Color(0xFFE65100),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                    )
                }
                items(
                    items = expiringSoonList,
                    key = { it.id }
                ) { food ->
                    FoodItemCard(
                        food = food,
                        onDeleteClick = { onDeleteFood(it) },
                        onCardClick = { onFoodClick(it) }
                    )
                }
            }

            if (normalList.isNotEmpty()) {
                item {
                    Text(
                        text = "Bình thường",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                    )
                }
                items(
                    items = normalList,
                    key = { it.id }
                ) { food ->
                    FoodItemCard(
                        food = food,
                        onDeleteClick = { onDeleteFood(it) },
                        onCardClick = { onFoodClick(it) }
                    )
                }
            }
        }
    }
}