package com.example.fridge.data.repository

import com.example.fridge.data.dao.FoodDao
import com.example.fridge.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val dao: FoodDao) {
    fun getAllFood(): Flow<List<FoodItem>> {
        return dao.getAllFood()
    }

    suspend fun addFood(food: FoodItem) {
        dao.insertFood(food)
    }

    suspend fun deleteFood(food: FoodItem) {
        dao.deleteFood(food)
    }

    suspend fun updateFood(food: FoodItem) {
        dao.updateFood(food)
    }
}