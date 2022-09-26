package com.example.personalfinances

import android.content.Intent
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


        // Listener for Cancel button
        binding.topAppBar.setNavigationOnClickListener { setResult(RESULT_CANCELED) }

        // Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm){

                // Collect data from Inputs
                val catName = binding.addCatName.text?.toString()
                Log.d(TAG, "CREATED NAME $catName")

                // TODO: Create dialog for choosing colors and icons
                val catColor = 1
                val catIcon = 3

                // Create intent, send there our data and finish this activity
                val intent = Intent()
                intent.putExtra(Utils.CAT_NAME_KEY, catName)
                intent.putExtra(Utils.CAT_ICON_KEY, catIcon)
                intent.putExtra(Utils.CAT_COLOR_KEY, catColor)

                setResult(RESULT_OK, intent)
                finish()
                true
            }else{
                false
            }
        }
    }
}