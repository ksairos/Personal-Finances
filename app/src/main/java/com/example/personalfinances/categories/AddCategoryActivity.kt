package com.example.personalfinances.categories

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.TypedArray
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.R
import com.example.personalfinances.databinding.ActivityAddCategoryBinding
import com.example.personalfinances.features.colorpicker.ColorAlertDialog


class AddCategoryActivity : AppCompatActivity() {

    private val TAG = "AddCategoryActivity"
    private lateinit var binding: ActivityAddCategoryBinding

    private lateinit var icons: TypedArray
    private lateinit var colors: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Arrays for Icon and Color resources
        icons = resources.obtainTypedArray(R.array.icons)
        colors = resources.obtainTypedArray(R.array.colors)

        // Listener for Cancel button
        binding.topAppBar.setNavigationOnClickListener {
            showExitAlertDialog()
        }

        binding.addCatColorBtn.setOnClickListener {
            showColorPickDialog()
        }

        // Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                val catName = binding.addCatName.text.toString()

                // Category name validation
                if (validateCatName(catName) == null){
//
//                    // For now we will use TextEdit to create Icon and Color of Category
//                    // TODO: Create dialog for choosing colors and icons. Don't forget to add a default values
//                    val catColorIdx = binding.addCatIcon.text.toString().toInt()
//                    val catIconIdx = binding.addCatColor.text.toString().toInt()
//
//                    // Create color resource and icon R.drawable.id and pass them into our Category instance
//                    val catColor = colors.getColor(catColorIdx, -1)
//                    val catIcon = icons.getResourceId(catIconIdx, -1)
//
//                    // Sending the data back to the MainActivity
//                    intent = Intent()
//                    intent.putExtra(Utils.CAT_NAME_KEY, catName)
//                    intent.putExtra(Utils.CAT_COLOR_KEY, catColor)
//                    intent.putExtra(Utils.CAT_ICON_KEY, catIcon)
//
//                    setResult(RESULT_OK, intent)
                    finish()
                }else{
                    // Display error
                    binding.addCatTxtLayoutName.helperText = validateCatName(catName)
                }
                true
            } else {
                false
            }
        }

    }

    // From using empty category name and the name with more than 20 symbols
    private fun validateCatName(catName: String): String? {
        if (catName == "" || catName.length > 20) {
            return "Required field"
        }
        return null
    }

    private fun showExitAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Discard changes and exit?")
        builder.setMessage("This action cannot be undone.")
        builder.setNegativeButton("Keep editing") {dialog, i -> }
        builder.setPositiveButton("Yes") {dialog, i ->
            setResult(RESULT_CANCELED, intent)
            finish()}
        builder.show()
    }

    private fun showColorPickDialog(){
        ColorAlertDialog().show(supportFragmentManager, "ColorPickFragment")
    }
}