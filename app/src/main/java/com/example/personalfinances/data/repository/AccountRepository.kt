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
    suspend fun insertAcc(account: Account){
        accDao.insertAcc(account)
    }

    @WorkerThread
    suspend fun deleteAcc(account: Account){
        accDao.deleteAcc(account)
    }

    @WorkerThread
    suspend fun updateAcc(account: Account){
        accDao.updateAcc(account)
    }

    @WorkerThread
    suspend fun getAccByName(name: String?): Account {
        return accDao.getAccByName(name)
    }
}