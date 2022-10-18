package com.example.personalfinances.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.personalfinances.data.AccDao
import com.example.personalfinances.data.Account
import kotlinx.coroutines.flow.Flow

class AccountRepository(private val accDao: AccDao) {

    val allAccounts: Flow<List<Account>> = accDao.getAll()
    val allAccountsIds: LiveData<List<Int?>> = accDao.getAllIds()
    val allAccountsNames: LiveData<List<String?>> = accDao.getAllNames()
    val sumBalance: LiveData<Double?> = accDao.sumBalance()

    @WorkerThread
    suspend fun insert(account: Account){
        accDao.insert(account)
    }

    @WorkerThread
    suspend fun delete(account: Account){
        accDao.delete(account)
    }

    suspend fun getAccIdByName(name: String?): Int?{
        return accDao.getAccIdByName(name)
    }
}