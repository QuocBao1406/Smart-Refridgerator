package com.example.fridge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fridge.data.model.FoodItem
import com.example.fridge.data.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FridgeViewModel(private val repository: FoodRepository) : ViewModel() {
    val allFoodItem: Flow<List<FoodItem>> = repository.getAllFood()

    fun addFood(name: String, image:String, quantity: Double, unit: String, type: String, expiry: Long) {
        val newFood = FoodItem(
            name = name,
            image = image,
            quantity = quantity,
            unit = unit,
            type = type,
            addedDate = System.currentTimeMillis(),
            expiry = expiry,
            status = 1
        )

        viewModelScope.launch {
            repository.addFood(newFood)
        }
    }

    fun deleteFood(food: FoodItem) {
        viewModelScope.launch {
            repository.deleteFood(food)
        }
    }

    fun updateFood(food: FoodItem) {
        viewModelScope.launch {
            repository.updateFood(food)
        }
    }
}
