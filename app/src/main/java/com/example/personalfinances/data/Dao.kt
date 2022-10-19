package com.example.personalfinances.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<Category>>

    @Delete
    suspend fun delete(category: Category)

    @Query("DELETE FROM category")
    suspend fun nukeCats()

    @Query("SELECT SUM(expanses) as sum_expanses FROM category")
    fun expansesSum(): Double?

//    @Query("SELECT * FROM category WHERE id=:id")
//    fun
}

@Dao
interface AccDao {
    @Insert
    suspend fun insertAcc(account: Account)

    @Query("SELECT * FROM account ORDER BY favorite DESC")
    fun getAll(): Flow<List<Account>>

    @Delete
    suspend fun deleteAcc(account: Account)

    @Update
    suspend fun updateAcc(account: Account)

    @Query("DELETE FROM account")
    suspend fun nukeAccs()

    @Query("SELECT SUM(balance) as sum_balance FROM account")
    fun sumBalance(): LiveData<Double?>

    @Query("SELECT id FROM account WHERE name=:name")
    suspend fun getAccIdByName(name: String?): Int

    @Query("SELECT * FROM account WHERE name=:name")
    suspend fun getAccByName(name: String?): Account

    @Query("SELECT name FROM account")
    fun getAllNames(): LiveData<List<String?>>

    @Query("SELECT id FROM account")
    fun getAllIds(): LiveData<List<Int?>>

//    @Query("SELECT * FROM account WHERE favorite=1")
//    suspend fun selectFavoriteAccount()
}

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<Transaction>>

    @Delete
    suspend fun delete(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)
}