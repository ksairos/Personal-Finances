package com.example.personalfinances

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.personalfinances.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CategoryAdapter
    private val catDb by lazy { MainDb.getDb(this).catDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        observeCatDb()
    }

    // this launcher allows us to get results from our another activity
    private var launcher: ActivityResultLauncher<Intent>? =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK -> {

                    val catName = result.data?.getStringExtra(Utils.CAT_NAME_KEY)
                    val catColor = result.data?.getIntExtra(Utils.CAT_COLOR_KEY, 0)
                    val catIcon = result.data?.getIntExtra(Utils.CAT_ICON_KEY, 0)

                    val newCategory = Category(null, catName, 0, catIcon, catColor)

                    lifecycleScope.launch {
                        catDb.insert(newCategory)
                    }
                    Toast.makeText(this, "A new category is added", Toast.LENGTH_SHORT).show()
                }
                RESULT_CANCELED -> {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /*
    This function is used to observe changes in our database. Whenever data is changed, the code in
    the curly braces us run. In our case this updates the content in our adapter.
     */
    private fun observeCatDb() {
        lifecycleScope.launch {
            catDb.getAll().collect { categoryList ->
                if (categoryList.isNotEmpty()) {
                    adapter.submitList(categoryList)

                // Set top bar data
                binding.toolBar.title = catDb.expansesSum().toString()
                }
            }
        }
    }

    // This function is used to initialize views and their inner content
    private fun init() {
        binding.apply {
            // Set Recycler View
            categoryRecView.layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = CategoryAdapter()
            categoryRecView.adapter = adapter

            // Set Listener for FAB that creates new categories
            addCategoryFab.setOnClickListener {
                val intent = Intent(this@MainActivity, AddCategoryActivity::class.java)
                launcher?.launch(intent)
            }

            // Set selected item to be Categories
            botNav.selectedItemId = R.id.bot_nav_categories
        }
    }
}