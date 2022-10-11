package com.example.personalfinances.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.personalfinances.Utils

@Database (entities = [Category::class, Account::class],
    exportSchema = true,
    version = 1
)

@TypeConverters(Utils.DateConverters::class)
abstract class MainDb : RoomDatabase()
{

    abstract fun catDao(): CatDao
    abstract fun accDao(): AccDao

    companion object {

        @Volatile
        private var INSTANCE: MainDb? = null

        fun getDb(context: Context): MainDb {

            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "local.db"
            ).build()
        }
    }

}