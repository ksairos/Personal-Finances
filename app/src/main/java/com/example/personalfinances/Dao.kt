package com.example.personalfinances

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert
    fun insert(category: Category)
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>
    @Delete
    fun delete(category: Category)
    @Query("DELETE FROM category")
    fun nukeCats()
}