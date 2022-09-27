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
        // TODO: Put LiveData into RecyclerView
        db.getDao().getAll().asLiveData().observe(this){
            it.forEach{ category ->
                binding.categoryName.text = category.name
                binding.categoryIcon.setBackgroundColor(category.color)
                binding.categoryIcon.setIconResource(category.icon)
                val text = "$" + category.expanses.toString()
                binding.categoryPrice.text = text
            }
        }

        // use launcher to start AddCategoryActivity when FAB is pressed
        binding.addCategoryFab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCategoryActivity::class.java)
//            launcher?.launch(intent)
            startActivity(intent)
        }
    }
}