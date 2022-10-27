package com.example.personalfinances

import android.app.Application
import com.example.personalfinances.data.MainDb
import com.example.personalfinances.data.repository.AccountRepository
import com.example.personalfinances.data.repository.CategoriesRepository
import com.example.personalfinances.data.repository.TransactionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PersonalFinancesApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MainDb.getDb(this, applicationScope)}
    val accRepository by lazy { AccountRepository(database.accDao()) }
    val catRepository by lazy { CategoriesRepository(database.catDao()) }
    val transactionsRepository by lazy { TransactionsRepository(database.transactionsDao()) }
}