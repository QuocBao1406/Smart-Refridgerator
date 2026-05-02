package com.example.fridge.data.repository

import com.example.fridge.data.dao.NotificationDao
import com.example.fridge.data.model.NotificationItem
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val dao: NotificationDao) {

    fun getAllNotification(): Flow<List<NotificationItem>> {
        return dao.getAllNotifications()
    }

    fun getUnreadCount(): Flow<Int> {
        return dao.getUnreadCount()
    }

    suspend fun addNotification(notification: NotificationItem) {
        return dao.insertNotification(notification)
    }

    suspend fun markAllAsRead() {
        return dao.markAllAsRead()
    }

    suspend fun deleteNotification(notification: NotificationItem) {
        return dao.deleteNotification(notification)
    }

    suspend fun hasNotification(foodId: Int, type: String): Boolean {
        return dao.hasNotification(foodId, type)
    }
}