package com.example.fridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.fridge.data.database.FridgeDatabase
import com.example.fridge.data.repository.FoodRepository
import com.example.fridge.data.repository.NotificationRepository
import com.example.fridge.ui.screens.FridgeScreen
import com.example.fridge.ui.viewmodel.FridgeViewModel
import com.example.fridge.ui.viewmodel.NotificationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo Database duy nhất cho toàn app
        val database = Room.databaseBuilder(
            applicationContext,
            FridgeDatabase::class.java,
            "fridge-db"
        )
            .fallbackToDestructiveMigration() // Thêm dòng này để tránh crash khi nâng cấp version
            .build()

        val foodRepository = FoodRepository(database.foodDao())
        val notificationRepository = NotificationRepository(database.notificationDao())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(FridgeViewModel::class.java) ->
                        FridgeViewModel(foodRepository) as T
                    modelClass.isAssignableFrom(NotificationViewModel::class.java) ->
                        NotificationViewModel(
                            notificationRepository,
                            foodRepository
                        ) as T
                    else -> throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }

        setContent {
            MaterialTheme {
                val fridgeViewModel: FridgeViewModel = viewModel(factory = factory)
                val notificationViewModel: NotificationViewModel = viewModel(factory = factory)

                FridgeScreen(
                    fridgeViewModel = fridgeViewModel,
                    notificationViewModel = notificationViewModel
                )
            }
        }
    }
}
