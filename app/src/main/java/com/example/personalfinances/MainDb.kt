package com.example.personalfinances

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Category::class], version = 1)
abstract class MainDb : RoomDatabase() {

    abstract fun getDao(): CatDao

    companion object{
        fun getDb(context: Context): MainDb{
            return Room.databaseBuilder(
                context,
                MainDb::class.java,
                "test.db"
            ).build()
        }
    }
}