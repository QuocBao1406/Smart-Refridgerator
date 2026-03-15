package com.example.fridge.data.repository

import com.example.fridge.data.dao.FoodDao
import com.example.fridge.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val foodDao: FoodDao) {
    val allFood: Flow<List<FoodItem>> = foodDao.getAllFood()

    suspend fun insert(food: FoodItem) {
        foodDao.insertFood(food)
    }

    suspend fun delete(food: FoodItem) {
        foodDao.deleteFood(food)
    }

    suspend fun update(food: FoodItem) {
        foodDao.updateFood(food)
    }

    suspend fun getFoodNamesForAI(): List<String> {
        return foodDao.getAllFoodNames()
    }
}