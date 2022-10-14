package com.example.personalfinances.data.repository

import androidx.annotation.WorkerThread
import com.example.personalfinances.data.CatDao
import com.example.personalfinances.data.Category
import kotlinx.coroutines.flow.Flow

class CategoriesRepository(private val catDao: CatDao) {

    val allCategories: Flow<List<Category>> = catDao.getAll()
//    val expansesSum = catDao.expansesSum()

    @WorkerThread
    suspend fun insert(category: Category){
        catDao.insert(category)
    }

    @WorkerThread
    suspend fun delete(category: Category){
        catDao.delete(category)
    }



}