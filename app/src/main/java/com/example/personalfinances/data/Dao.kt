package com.example.personalfinances.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinances.data.Account
import com.example.personalfinances.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert
    suspend fun insert(category: Category)
    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<Category>>
    @Delete
    suspend fun delete(category: Category)
    @Query("DELETE FROM category")
    suspend fun nukeCats()
    @Query("SELECT SUM(expanses) as sum_expanses FROM category")
    suspend fun expansesSum(): Long?
}

@Dao
interface AccDao {
    @Insert
    suspend fun insert(account: Account)
    @Query("SELECT * FROM account")
    fun getAll(): Flow<List<Account>>
    @Delete
    suspend fun delete(account: Account)
    @Query("DELETE FROM account")
    suspend fun nukeAccs()
    @Query("SELECT SUM(balance) as sum_balance FROM account")
    suspend fun sumBalance(): Long?
//    @Query("SELECT * FROM account WHERE favorite=1")
//    suspend fun selectFavoriteAccount()
}