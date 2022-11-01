package com.example.personalfinances

import android.app.Application
import com.example.personalfinances.data.MainDb
import com.example.personalfinances.data.repository.AccountRepository
import com.example.personalfinances.data.repository.CategoriesRepository
import com.example.personalfinances.data.repository.TransactionsRepository
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*

class PersonalFinancesApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MainDb.getDb(this, applicationScope)}
    val accRepository by lazy { AccountRepository(database.accDao()) }
    val catRepository by lazy { CategoriesRepository(database.catDao()) }
    val transactionsRepository by lazy { TransactionsRepository(database.transactionsDao()) }


    //? This section is used in Icon Picker implementation
    var iconPack: IconPack? = null
    override fun onCreate() {
        super.onCreate()
        // Load the icon pack on application start.
        loadIconPack()
    }

    private fun loadIconPack() {
        // Create an icon pack loader with application context.
        val loader = IconPackLoader(this)
        // Create an icon pack and load all drawables.
        val iconPack = loader.load(R.xml.icons, R.xml.tags, listOf(Locale.ENGLISH))
        iconPack.loadDrawables(loader.drawableLoader)
        this.iconPack = iconPack
    }
}