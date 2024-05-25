package com.example.budgetmanager

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Transection::class), version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun transectionDao():transectionDAO
}