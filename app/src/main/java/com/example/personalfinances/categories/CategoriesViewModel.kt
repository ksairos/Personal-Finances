package com.example.personalfinances.categories

import androidx.lifecycle.*
import com.example.personalfinances.data.Category
import com.example.personalfinances.data.repository.CategoriesRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repository: CategoriesRepository): ViewModel() {

    // Create a LiveData from the Flow of our category repository
    val allCats: LiveData<List<Category>> = repository.allCategories.asLiveData()

    // An insert function for inserting the data in our repository. This is done to separate data and UI
    fun insertCat(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }

    // A delete function for the data in our repository
    fun deleteCat(category: Category) = viewModelScope.launch {
        repository.delete(category)
    }
}

class CategoriesViewModelFactory(private val repository: CategoriesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}