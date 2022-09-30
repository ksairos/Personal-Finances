package com.example.personalfinances

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.internal.synchronized

@Database (entities = [Category::class], exportSchema = true, version = 1)
abstract class MainDb : RoomDatabase()
// TODO: Create TypeConverter for dates - https://johncodeos.com/how-to-use-room-in-android-using-kotlin/
{

    abstract fun catDao(): CatDao

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
                "test.db"
            ).build()
        }
    }

}