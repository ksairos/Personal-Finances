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
        init(db)
    }

    private fun init(db: MainDb){
        binding.apply{

            categoryRecView.layoutManager = GridLayoutManager(this@MainActivity, 4)

            Thread{
                categoryRecView.adapter = CategoryAdapter(db.getDao().getAll())
            }.start()

            // Set Listener for FAB that creates new categories
            addCategoryFab.setOnClickListener {
                val intent = Intent(this@MainActivity, AddCategoryActivity::class.java)
                startActivity(intent)
            }

        }
    }
}