package com.example.personalfinances

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.personalfinances.databinding.ActivityAddCategoryBinding

class AddCategoryActivity : AppCompatActivity() {

    private val TAG = "AddCategoryActivity"
    private lateinit var binding: ActivityAddCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Arrays for Icon and Color resources
        val icons = resources.obtainTypedArray(R.array.icons)
        val colors = resources.obtainTypedArray(R.array.colors)

        // Initialize our DataBase
        val db = MainDb.getDb(this)

        // Listener for Cancel button
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm){

                // Collect data from Inputs
                val catName = binding.addCatName.text.toString()
                Log.d(TAG, "CREATED NAME $catName")

                // For now we will use TextEdit to create Icon and Color of Category
                // TODO: Create dialog for choosing colors and icons
<<<<<<< HEAD
                val catColor = binding.addCatColor.text?.toString()?.toInt()
                val catIcon = binding.addCatIcon.text?.toString()?.toInt()
=======
                val catColorIdx = binding.addCatIcon.text.toString().toInt()
                val catIconIdx = binding.addCatColor.text.toString().toInt()

                // Create color resource and icon R.drawable.id and pass them into our Category instance
                val catColor = colors.getColor(catColorIdx, -1)
                val catIcon = icons.getResourceId(catIconIdx, -1)
>>>>>>> AddingDB

                val newCategory = Category(null, catName, 0, catIcon, catColor)

                // In order to insert new Category to our DB use Threads or
                // TODO: Try using Coroutines in order to insert data to our DB
                Thread{
                    db.getDao().insert(newCategory)
                }.start()

                finish()
                true
            }else{
                false
            }
        }

    }
}