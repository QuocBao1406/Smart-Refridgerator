package com.example.fridge

import android.app.Application
import com.example.fridge.data.FridgeDatabase
import com.example.fridge.data.repository.FoodRepository

class FridgeApplication : Application() {
    val database by lazy { FridgeDatabase.getDatabase(this) }
    val repository by lazy { FoodRepository(database.foodDao()) }
}