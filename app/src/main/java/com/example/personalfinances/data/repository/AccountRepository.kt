package com.example.personalfinances.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.personalfinances.data.AccDao
import com.example.personalfinances.data.Account
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val accDao: AccDao) {

    val allAccounts: Flow<List<Account>> = accDao.getAll()
    val sumBalance: LiveData<Double?> = accDao.sumBalance()

    @WorkerThread
    suspend fun insert(account: Account){
        accDao.insert(account)
    }

    @WorkerThread
    suspend fun delete(account: Account){
        accDao.delete(account)
    }
}