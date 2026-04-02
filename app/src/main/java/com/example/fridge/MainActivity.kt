package com.example.fridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.fridge.data.database.FridgeDatabase
import com.example.fridge.data.repository.FoodRepository
import com.example.fridge.ui.screens.FridgeScreen
import com.example.fridge.ui.viewmodel.FridgeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            FridgeDatabase::class.java,
            "fridge-db"
        ).build()

        val repository = FoodRepository(database.foodDao())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FridgeViewModel(repository) as T
            }
        }

        setContent {
            MaterialTheme {
                val viewModel: FridgeViewModel = viewModel(factory = factory)

                FridgeScreen(viewModel = viewModel)
            }
        }
    }
}