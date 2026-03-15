package com.example.fridge

import android.app.Application
import com.example.fridge.data.database.AppDatabase

class MyApplication : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}