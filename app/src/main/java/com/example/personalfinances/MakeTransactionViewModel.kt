package com.example.personalfinances

import androidx.lifecycle.*
import com.example.personalfinances.data.Account
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


    // This function creates a transaction between account and a category
    fun createTransactionToCategory(fromAcc: Account, toId: Int?, amount: Double, date: String?) {

        // Create and insert Transaction
        val newTransaction = Transaction(null, fromAcc.id, toId, amount, date)
        insertTransaction(newTransaction)

        // Update Account by subtracting money from the balance
        val newBalance = fromAcc.balance?.minus(amount)
        val updatedAccount = Account(fromAcc.id, fromAcc.name, newBalance, fromAcc.favorite, fromAcc.icon, fromAcc.color)
        updateAcc(updatedAccount)

        // Update Category by adding money to the Expenses section

    }

    // This function creates a transaction between two accounts
    fun createTransactionToAccount() {
        // Create and insert Transaction

        // Subtract money from Account

        // Add money to the recipient Account Balance
    }


    // Function to insert Transaction in our database
    private fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    // Function to Update Account balance
    private fun updateAcc(account: Account) = viewModelScope.launch {
        acc_repository.updateAcc(account)
    }

    // Function to Update Category expenses

    // Get Account using its Name
    fun getAccByName(name: String?): MutableLiveData<Account> {
        val result = MutableLiveData<Account>()
        viewModelScope.launch { result.postValue(acc_repository.getAccByName(name)) }
        return result
    }
}


// The model factory for our viewModel
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