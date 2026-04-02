package com.example.fridge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: String,
    val quantity: Double,
    val unit: String,
    val type: String,
    val addedDate: Long,
    val expiry: Long,
    val status: Int
)
