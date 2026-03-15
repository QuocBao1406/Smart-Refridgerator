package com.example.fridge.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fridge.data.dao.FoodDao
import com.example.fridge.data.model.FoodItem
import androidx.room.Room

@Database(entities = [FoodItem::class], version = 1, exportSchema = false)
abstract class FridgeDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FridgeDatabase? = null

        fun getDatabase(context: Context): FridgeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FridgeDatabase::class.java,
                    "fridge_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}