package com.example.personalfinances

import androidx.lifecycle.*
import com.example.personalfinances.accounts.AccountsViewModel
import com.example.personalfinances.data.Account
import com.example.personalfinances.data.Category
import com.example.personalfinances.data.Transaction
import com.example.personalfinances.data.repository.AccountRepository
import com.example.personalfinances.data.repository.CategoriesRepository
import com.example.personalfinances.data.repository.TransactionsRepository
import kotlinx.coroutines.launch

class MakeTransactionViewModel(private val repository: TransactionsRepository,
                               private val acc_repository: AccountRepository,
                               private val cat_repository: CategoriesRepository): ViewModel() {

    val allTransactions: LiveData<List<Transaction>> = repository.allTransactions.asLiveData()

    val allAccountsNames: LiveData<List<String?>> = acc_repository.allAccountsNames
    val allAccountsIds: LiveData<List<Int?>> = acc_repository.allAccountsIds

    val allCategories: LiveData<List<Category>> = cat_repository.allCategories.asLiveData()



    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }
}

class MakeTransactionViewModelFactory(private val repository: TransactionsRepository,
                                      private val acc_repository: AccountRepository,
                                      private val cat_repository: CategoriesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakeTransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MakeTransactionViewModel(repository, acc_repository, cat_repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}