package com.example.personalfinances

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.personalfinances.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize our DB
        val db = MainDb.getDb(this)
        // Use LiveData to observe the data in our Database
//        db.getDao().getAll().asLiveData().observe(this){
//            it.forEach{ category ->
//                binding.categoryName.text = category.name
//                binding.categoryIcon.setBackgroundColor(category.color)
//                binding.categoryIcon.setIconResource(category.icon)
//                val text = "$" + category.expanses.toString()
//                binding.categoryPrice.text = text
//            }
//        }

        // this launcher allows us to get results from our another activity
        init(db)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK -> {

                    val catName = result.data?.getStringExtra(Utils.CAT_NAME_KEY)
                    val catColor = result.data?.getIntExtra(Utils.CAT_COLOR_KEY, 0)
                    val catIcon = result.data?.getIntExtra(Utils.CAT_ICON_KEY, 0)

                    val newCategory = Category(null, catName, 0, catIcon, catColor)

                    // In order to insert new Category to our DB use Threads or
                    // TODO: Try using Coroutines in order to insert data to our DB
                    adapter.addCategory(newCategory, db)

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
    }

    private fun init(db: MainDb){
        binding.apply{

            categoryRecView.layoutManager = GridLayoutManager(this@MainActivity, 4)

            Thread{
                adapter = CategoryAdapter(db.getDao().getAll())
            }.start()
            categoryRecView.adapter = adapter

            // Set Listener for FAB that creates new categories
            addCategoryFab.setOnClickListener {
                val intent = Intent(this@MainActivity, AddCategoryActivity::class.java)
                launcher?.launch(intent)
            }

        }
    }
}