package com.example.personalfinances

import androidx.lifecycle.*
import com.example.personalfinances.accounts.AccountsViewModel
import com.example.personalfinances.data.Account
import com.example.personalfinances.data.repository.AccountRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AccountRepository): ViewModel() {

    // Create a LiveData from the Flow of our account repository
    val allAccounts: LiveData<List<Account>> = repository.allAccounts.asLiveData()

    // Get the sum of the balance of all accounts in our database
    var sumBalance: LiveData<Double?> = repository.sumBalance
}

class MainActivityViewModelFactory(private val repository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}