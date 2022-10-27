package com.example.personalfinances.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.personalfinances.data.AccDao
import com.example.personalfinances.data.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.toList

class AccountRepository(private val accDao: AccDao) {
    private val TAG = "\nLOOK AT ME:"
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