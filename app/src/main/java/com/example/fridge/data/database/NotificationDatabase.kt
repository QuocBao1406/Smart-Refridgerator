package com.example.fridge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fridge.data.dao.NotificationDao
import com.example.fridge.data.model.NotificationItem

@Database(entities = [NotificationItem::class], version = 1)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}