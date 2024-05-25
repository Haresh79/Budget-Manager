package com.example.budgetmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface transectionDAO {
    @Query("SELECT * FROM transection")
    fun getAll():LiveData<List<Transection>>
    @Insert
    fun insert(vararg transection: Transection)
    @Delete
    fun delete(transection: Transection)
    @Query("DELETE FROM transection")
    fun deleteAll()
}