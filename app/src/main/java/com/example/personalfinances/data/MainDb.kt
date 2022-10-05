package com.example.personalfinances.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Category::class, Account::class],
    exportSchema = true,
    version = 1
)
abstract class MainDb : RoomDatabase()
// TODO: Create TypeConverter for dates - https://johncodeos.com/how-to-use-room-in-android-using-kotlin/
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