package com.example.personalfinances

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insert(category: Category)
    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<Category>>
    @Delete
    fun delete(category: Category)
}