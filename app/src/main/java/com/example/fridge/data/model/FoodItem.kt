package com.example.fridge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "food_name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "unit") val unit: String,
    @ColumnInfo(name = "expiry_date") val expiryDate: Long,
    @ColumnInfo(name = "added_date") val addedDate: Long,
    @ColumnInfo(name = "status") val status: Int = 1,
)
