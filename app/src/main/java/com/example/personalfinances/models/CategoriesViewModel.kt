package com.example.personalfinances.models
import androidx.lifecycle.*
import com.example.personalfinances.data.Category
import com.example.personalfinances.data.repository.CategoriesRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(private val cat_repository: CategoriesRepository): ViewModel() {

    // Create a LiveData from the Flow of our category repository
    val allCats: LiveData<List<Category>> = cat_repository.allCategories.asLiveData()

    // An insert function for inserting the data in our repository. This is done to separate data and UI
    fun insertCat(category: Category) = viewModelScope.launch {
        cat_repository.insertCat(category)
    }

    fun updateCat(category: Category) = viewModelScope.launch {
        cat_repository.updateCat(category)
    }

    // Get Category using its ID
    fun getCatById(id: Int?): MutableLiveData<Category> {
        val result = MutableLiveData<Category>()
        viewModelScope.launch { result.postValue(cat_repository.getCatById(id)) }
        return result
    }

    // A delete function for the data in our repository
    fun deleteCat(category: Category) = viewModelScope.launch {
        cat_repository.deleteCat(category)
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