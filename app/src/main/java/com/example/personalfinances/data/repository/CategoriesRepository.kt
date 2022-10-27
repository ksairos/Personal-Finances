package com.example.personalfinances.data.repository

import androidx.annotation.WorkerThread
import com.example.personalfinances.data.CatDao
import com.example.personalfinances.data.Category
import kotlinx.coroutines.flow.Flow

class CategoriesRepository(private val catDao: CatDao) {

    val allCategories: Flow<List<Category>> = catDao.getAll()
//    val expansesSum = catDao.expansesSum()

    @WorkerThread
    suspend fun insertCat(category: Category){
        catDao.insertCat(category)
    }

    @WorkerThread
    suspend fun deleteCat(category: Category){
        catDao.deleteCat(category)
    }

    @WorkerThread
    suspend fun updateCat(category: Category){
        catDao.updateCat(category)
    }

    @WorkerThread
    suspend fun getCatById(id: Int?): Category {
        return catDao.getCatById(id)
    }




}