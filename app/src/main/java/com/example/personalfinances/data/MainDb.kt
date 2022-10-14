package com.example.personalfinances.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.personalfinances.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database (entities = [Category::class, Account::class],
    exportSchema = false,
    version = 1
)

@TypeConverters(Utils.DateConverters::class)
abstract class MainDb : RoomDatabase() {

    abstract fun catDao(): CatDao
    abstract fun accDao(): AccDao

    //
    private class MainCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.accDao())
                }
            }
        }

        suspend fun populateDatabase(accDao: AccDao) {
            // Delete all content here.
            accDao.nukeAccs()
        }
    }


    companion object {

        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: MainDb? = null

        fun getDb(context: Context, scope: CoroutineScope): MainDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "word_database"
                ).addCallback(MainCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

//        fun getDb(context: Context): MainDb {
//
//            if (INSTANCE == null) {
//                INSTANCE = buildDatabase(context)
//            }
//            return INSTANCE!!
//        }
//
//        private fun buildDatabase(context: Context): MainDb {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                MainDb::class.java,
//                "local.db"
//            ).build()
//        }
    }

}