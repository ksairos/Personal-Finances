package com.example.personalfinances.categories

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.databinding.ActivityAddCategoryBinding
import com.example.personalfinances.features.colorpicker.ColorAlertDialog
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog


class AddCategoryActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener {

    private val TAG = "AddCategoryActivity"
    private lateinit var binding: ActivityAddCategoryBinding

    private lateinit var icons: TypedArray
    private lateinit var colors: TypedArray

    private var pickedColor: Int? = null

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
//            showColorPickDialog()
            callColorDialog()
        }

        // Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                val catName = binding.addCatName.text.toString()

                // Category name validation
                if (validateCatName(catName) == null){

                    // Create color resource and icon R.drawable.id and pass them into our Category instance
                    var catColor = Utils.colorArray(this)[0]

                    if (pickedColor != null){
                        catColor = pickedColor as Int
                    }
//                    val catIcon = icons.getResourceId(catIconIdx, -1)

                    // Sending the data back to the MainActivity
                    intent = Intent()
                    intent.putExtra(Utils.CAT_NAME_KEY, catName)
                    intent.putExtra(Utils.CAT_COLOR_KEY, catColor)
                    intent.putExtra(Utils.CAT_ICON_KEY, 0)

                    setResult(RESULT_OK, intent)
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


    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {
        if (dialogTag == Utils.COLOR_PICK_TAG && which == SimpleDialog.OnDialogResultListener.BUTTON_POSITIVE) {
            pickedColor = extras.getInt(SimpleColorDialog.COLOR)
            return true
        }
        return false
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

    private fun callColorDialog(){
        SimpleColorDialog.build()
            .title(getString(R.string.icon_color))
            .colors(Utils.colorArray(this))
            .show(this, Utils.COLOR_PICK_TAG)
    }
}