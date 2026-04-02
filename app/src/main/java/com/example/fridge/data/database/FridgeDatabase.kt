package com.example.fridge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fridge.data.dao.FoodDao
import com.example.fridge.data.model.FoodItem

@Database(entities = [FoodItem::class], version = 1)
abstract class FridgeDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}