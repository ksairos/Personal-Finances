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
//        val db = MainDb.getDb(this)

        // Listener for Cancel button
        binding.topAppBar.setNavigationOnClickListener { setResult(RESULT_CANCELED) }

        // Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm){

                // Collect data from Inputs
                val catName = binding.addCatName.text.toString()
                Log.d(TAG, "CREATED NAME $catName")

                // For now we will use TextEdit to create Icon and Color of Category
                // TODO: Create dialog for choosing colors and icons
                val catColorIdx = binding.addCatIcon.text.toString().toInt()
                val catIconIdx = binding.addCatColor.text.toString().toInt()

//                val catColor = colors.getColor(catColorIdx, -1)
//                val catIcon = icons.getDrawable(catIconIdx)

//                val newCategory = Category(null, catName, 0, catIcon, catColor)
//
//                Thread{
//                    db.getDao().insert(newCategory)
//                }.start()

                // Create intent, send there our data and finish this activity
                val intent = Intent()
                intent.putExtra(Utils.CAT_NAME_KEY, catName)
                intent.putExtra(Utils.CAT_ICON_KEY, catIconIdx)
                intent.putExtra(Utils.CAT_COLOR_KEY, catColorIdx)

//                intent.putExtra(Utils.CAT_NAME_KEY, catName)
//                intent.putExtra(Utils.CAT_ICON_KEY, catIcon)
//                intent.putExtra(Utils.CAT_COLOR_KEY, catColor)

                setResult(RESULT_OK, intent)
                finish()
                true
            }else{
                false
            }
        }

    }
}