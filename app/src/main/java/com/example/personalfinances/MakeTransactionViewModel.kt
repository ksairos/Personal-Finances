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
    fun createTransaction(fromAcc: Account, toId: Int?, amount: Double, date: String?) {

        // Create and insert Transaction in our database
        val newTransaction = Transaction(null, fromAcc.id, toId, amount, date)
        viewModelScope.launch { repository.insert(newTransaction) }
    }

    // This function creates a transaction between two accounts
    fun createTransactionToAccount() {
        // Create and insert Transaction

        // Subtract money from Account

        // Add money to the recipient Account Balance
    }


    // Function to Update Account balance
    fun updateAcc(account: Account, amount: Double, recipient: Boolean = false) {

        // If the chosen Account is a recipient, add money to the Balance. Otherwise subtract
        if (recipient){
            val newBalance = account.balance?.plus(amount)
            val updatedAccount = Account(account.id, account.name, newBalance, account.favorite, account.icon, account.color)
            viewModelScope.launch { acc_repository.updateAcc(updatedAccount) }
        }else{
            // Update Account by subtracting money from the balance
            val newBalance = account.balance?.minus(amount)
            val updatedAccount = Account(account.id, account.name, newBalance, account.favorite, account.icon, account.color)
            viewModelScope.launch { acc_repository.updateAcc(updatedAccount) }
        }

    }

    // Function to Update Category expenses
    fun updateCat(category: Category, amount: Double){
        val newExpanses = category.expanses?.plus(amount)
        val updatedCategory = Category(category.id, category.name, newExpanses, category.icon, category.color)
        viewModelScope.launch { cat_repository.updateCat(updatedCategory) }
    }


    // Get Account using its Name
    fun getAccByName(name: String?): MutableLiveData<Account> {
        val result = MutableLiveData<Account>()
        viewModelScope.launch { result.postValue(acc_repository.getAccByName(name)) }
        return result
    }

    // Get Category using its ID
    fun getCatById(id: Int?): MutableLiveData<Category> {
        val result = MutableLiveData<Category>()
        viewModelScope.launch { result.postValue(cat_repository.getCatById(id)) }
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