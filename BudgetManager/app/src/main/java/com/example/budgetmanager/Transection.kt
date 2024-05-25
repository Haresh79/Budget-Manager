package com.example.budgetmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transection")
data class Transection(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val amount:Int
)
