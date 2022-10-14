package com.example.personalfinances.data.repository

import androidx.annotation.WorkerThread
import com.example.personalfinances.data.Transaction
import com.example.personalfinances.data.TransactionDao
import kotlinx.coroutines.flow.Flow

class TransactionsRepository(private val transactionDao: TransactionDao) {

    val allTransactions: Flow<List<Transaction>> = transactionDao.getAll()

    @WorkerThread
    suspend fun insert(transaction: Transaction){
        transactionDao.insert(transaction)
    }

    @WorkerThread
    suspend fun delete(transaction: Transaction){
        transactionDao.delete(transaction)
    }
}