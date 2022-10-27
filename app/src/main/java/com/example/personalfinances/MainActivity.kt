package com.example.personalfinances

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.personalfinances.databinding.ActivityMainBinding
import com.example.personalfinances.fragments.AccountsFragment
import com.example.personalfinances.fragments.CategoriesFragment
import com.example.personalfinances.models.MainActivityViewModel
import com.example.personalfinances.models.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    // Initialize our ViewModel
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as PersonalFinancesApplication).accRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeData()
        openFragment(R.id.fragment_container, CategoriesFragment())
    }

    /*
    This function is used to observe changes in our database. Whenever data is changed, the code in
    the curly braces us run. In our case this updates the content in our adapter.
     */
    // TODO: Add dependency injection (Dagger) to avoid database initialization everytime
    private fun observeData() {
        viewModel.sumBalance.observe(this) { balance ->
            if (balance == null) binding.toolBar.title = "$0"
            else binding.toolBar.title = String.format("$%.0f", balance)
        }

    }

    // This function is used to initialize views and their inner content
    private fun init() {
        binding.apply {
            // Set selected item to be CategoriesFragment
            botNav.selectedItemId = R.id.bot_nav_categories
        }

        // Set Listeners for bottom navigation
        binding.botNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_nav_categories -> {
                    openFragment(R.id.fragment_container, CategoriesFragment())
                    true
                }
                R.id.bot_nav_accounts -> {
                    openFragment(R.id.fragment_container, AccountsFragment())
                    true
                }
                R.id.bot_nav_transactions -> {
                    // TODO: add Transactions Fragment
                    openFragment(R.id.fragment_container, CategoriesFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(placeHolder: Int, frag: Fragment) {
        // This code replaces the data in our FragmentView with an instance of our Fragment Class
        supportFragmentManager
            .beginTransaction()
            .replace(placeHolder, frag)
            .commit()
    }
}