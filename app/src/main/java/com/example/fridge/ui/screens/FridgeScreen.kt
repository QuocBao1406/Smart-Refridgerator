package com.example.fridge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fridge.data.model.FoodItem
import com.example.fridge.ui.components.AddFoodDialog
import com.example.fridge.ui.components.FoodDetaildEditDialog
import com.example.fridge.ui.components.FoodItemCard
import com.example.fridge.ui.viewmodel.FridgeViewModel

@Composable
fun FridgeScreen(viewModel: FridgeViewModel) {
    val foodListState by viewModel.allFoodItem.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf<FoodItem?>(null)}
    var currentTab by remember { mutableStateOf(0) }

    MainLayout(
        title = "Smart Fridge",
        onFabClick = {
            showAddDialog = true
        },
        currentTab = currentTab,
        onTabSelected = { newTab -> currentTab = newTab }
    ) { paddingValues ->
        when (currentTab) {
            0 -> {
                HomeTab(
                    foodList = foodListState,
                    onDeleteFood = { viewModel.deleteFood(it) },
                    onFoodClick = { selectedFood = it },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            1 -> {
                Statistics(
                    foodList = foodListState,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            2 -> {
                Box(
                    modifier = Modifier.padding(paddingValues).fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Màn hình Cài đặt đang xây dựng ⚙\uFE0F")
                }
            }
        }

        if(showAddDialog == true) {
            AddFoodDialog(
                onDismiss = {
                    showAddDialog = false
                },
                onSave = { name, image, quantity, unit, type, expiry ->
                    viewModel.addFood(
                        name = name,
                        image = image,
                        quantity = quantity.toDoubleOrNull() ?: 0.0,
                        unit = unit,
                        type = type,
                        expiry = expiry
                    )

                    showAddDialog = false
                }
            )
        }

        selectedFood?.let { foodToEdit ->
            FoodDetaildEditDialog(
                food = foodToEdit,
                onDismiss = {
                    selectedFood = null
                },
                onSave = { newName, newImage, newQuantity, newUnit, newType, newExpiry ->
                    val updatedFood = foodToEdit.copy(
                        name = newName,
                        image = newImage,
                        quantity = newQuantity.toDoubleOrNull() ?: foodToEdit.quantity,
                        unit = newUnit,
                        type = newType,
                        expiry = newExpiry,
                    )

                    viewModel.updateFood(updatedFood)
                }
            )
        }
    }
}