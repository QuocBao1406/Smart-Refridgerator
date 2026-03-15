package com.example.fridge.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fridge.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_items WHERE status = 1 ORDER BY expiry_date ASC")
    fun getAllFood(): Flow<List<FoodItem>>

    @Query("SELECT food_name FROM food_items WHERE status = 1")
    suspend fun getAllFoodNames(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodItem)

    @Update
    suspend fun updateFood(food: FoodItem)

    @Delete
    suspend fun deleteFood(food: FoodItem)

    @Query("SELECT * FROM food_items WHERE food_name LIKE '%' || :searchQuery || '%' AND status = 1")
    fun searchFood(searchQuery: String): Flow<List<FoodItem>>
}