package com.example.personalfinances

import android.util.Log
import androidx.lifecycle.*
import com.example.personalfinances.data.Category
import com.example.personalfinances.data.Transaction
import com.example.personalfinances.data.repository.AccountRepository
import com.example.personalfinances.data.repository.CategoriesRepository
import com.example.personalfinances.data.repository.TransactionsRepository
import kotlinx.coroutines.launch

class MakeTransactionViewModel(
    private val repository: TransactionsRepository,
    private val acc_repository: AccountRepository,
    private val cat_repository: CategoriesRepository
) : ViewModel() {

    private val TAG = "MakeTransactionViewMode"

    val allTransactions: LiveData<List<Transaction>> = repository.allTransactions.asLiveData()

    val allAccountsNames: LiveData<List<String?>> = acc_repository.allAccountsNames
    val allAccountsIds: LiveData<List<Int?>> = acc_repository.allAccountsIds

    val allCategories: LiveData<List<Category>> = cat_repository.allCategories.asLiveData()

    private var temp: Unit? = null


    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    // This function creates a transaction between account and a category (or another account)
    fun createTransactionToCategory(fromName: String?, toId: Int?, amount: Double?, date: String?) {
        val fromId = getAccIdByName(fromName)

        // Create and insert Transaction
        val newTransaction: Transaction = Transaction(null, fromId, toId, amount, date)
        Log.d(
            TAG,
            "createTransactionToCategory: CREATED TRANSACTIONS: \nID: $fromId, \nTO ID: $toId, \nAMOUNT: $amount, \nDate:$date"
        )
        // Subtract money from Account

        // Add money to the recipient Category Expenses
    }

    fun createTransactionToAccount() {
        // Create and insert Transaction

        // Subtract money from Account

        // Add money to the recipient Account Balance
    }

    private fun getAccIdByName(name: String?): Int?{
        val result = MutableLiveData<Int?>()
        viewModelScope.launch { result.postValue(acc_repository.getAccIdByName(name).toString().toInt()) }
        return result.value
    }
}

class MakeTransactionViewModelFactory(
    private val repository: TransactionsRepository,
    private val acc_repository: AccountRepository,
    private val cat_repository: CategoriesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakeTransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MakeTransactionViewModel(repository, acc_repository, cat_repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}