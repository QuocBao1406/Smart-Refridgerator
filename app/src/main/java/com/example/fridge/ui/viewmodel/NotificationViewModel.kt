package com.example.fridge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fridge.data.model.FoodItem
import com.example.fridge.data.model.NotificationItem
import com.example.fridge.data.repository.FoodRepository
import com.example.fridge.data.repository.NotificationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationRepository: NotificationRepository,
    private val foodRepository: FoodRepository
): ViewModel() {
    val notifications: StateFlow<List<NotificationItem>> = notificationRepository.getAllNotification()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val unreadCount: StateFlow<Int> = notificationRepository.getUnreadCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun addNotification(foodId: Int,title: String, message: String, timestamp: Long, isRead: Boolean, type: String) {
        val newNotification = NotificationItem(
            foodId = foodId,
            title = title,
            message = message,
            timestamp = timestamp,
            isRead = isRead,
            type = type
        )

        viewModelScope.launch {
            notificationRepository.addNotification(newNotification)
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            notificationRepository.markAllAsRead()
        }
    }

    init {
        viewModelScope.launch {
            foodRepository.getAllFood().collect { foodList ->
                foodList.forEach { food ->
                    checkExpiryAndNotifySingle(food)
                }
            }
        }
    }

    private suspend fun checkExpiryAndNotifySingle(food: FoodItem) {
        val currentTime = System.currentTimeMillis()
        val diffMillis = food.expiry - currentTime
        val daysLeft = (diffMillis / (24*60*60*1000)).toInt()

        when {
            daysLeft < 0 -> {
                val isExists = notificationRepository.hasNotification(food.id, "expired")
                val daysExpiredLeft = Math.abs(daysLeft)

                if (!isExists) {
                    addNotification(
                        food.id,
                        "Đã quá hạn!",
                        "Hạn sử dụng của ${food.name} đã quá hạn $daysExpiredLeft ngày",
                        currentTime,
                        false,
                        "expired"
                    )
                }
            }
            daysLeft in 0..2 -> {
                val dayString = if (daysLeft == 0) "hôm nay" else "$daysLeft ngày nữa"

                val isExists = notificationRepository.hasNotification(food.id, "expiry")
                if (!isExists) {
                    addNotification(
                        food.id,
                        "Sắp hết hạn!",
                        "Hạn sử dụng của ${food.name} hết trong $dayString",
                        currentTime,
                        false,
                        "expiry"
                    )
                }
            }
        }
    }

    fun deleteNotification(notification: NotificationItem) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(notification)
        }
    }
}