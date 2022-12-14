package com.example.personalfinances.models

import androidx.lifecycle.*
import com.example.personalfinances.data.Account
import com.example.personalfinances.data.repository.AccountRepository
import kotlinx.coroutines.launch

class AccountsViewModel(private val repository: AccountRepository): ViewModel(){

    // Create a LiveData from the Flow of our account repository
    val allAccounts: LiveData<List<Account>> = repository.allAccounts.asLiveData()

    // An insert function for inserting the data in our repository. This is done to separate data and UI
    fun insertAcc(account: Account) = viewModelScope.launch {
        repository.insertAcc(account)
    }

    // Update function
    fun updateAcc(account: Account) = viewModelScope.launch {
        repository.updateAcc(account)
    }

    // Get Account using its id
    fun getAccById(id: Int?): MutableLiveData<Account> {
        val result = MutableLiveData<Account>()
        viewModelScope.launch { result.postValue(repository.getAccById(id)) }
        return result
    }

    // Get the sum of the balance of all accounts in our database
    fun sumBalance() = viewModelScope.launch {
        repository.sumBalance
    }

    // Get Favorite account
    fun getFavoriteAcc(): MutableLiveData<Account> {
        val result = MutableLiveData<Account>()
        viewModelScope.launch { result.postValue(repository.getFavoriteAcc()) }
        return result
    }

}

class AccountViewModelFactory(private val repository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
