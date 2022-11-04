package com.example.personalfinances.categories

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.databinding.ActivityAddCategoryBinding
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog


class AddCategoryActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener,
    IconDialog.Callback {

    private val TAG = "AddCategoryActivity"
    private lateinit var binding: ActivityAddCategoryBinding

    private var pickedColor: Int? = null
    private var pickedIconId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Default values for the category Color and Icon
        var catColor: Int? = Utils.colorArray(this)[0]
        var catIconId: Int? = 0

        // Set default color and icon to the test icon
        catColor?.let { binding.testCategory.setBackgroundColor(it) }
        binding.testCategory.icon = catIconId?.let {
            PersonalFinancesApplication.instance.iconPack?.getIcon(
                it
            )?.drawable
        }

        // Listener for Cancel button
        binding.topAppBar.setNavigationOnClickListener {
            showExitAlertDialog()
        }

        // Listener for Color Pick button
        binding.addCatColorBtn.setOnClickListener {
            showColorPickerDialog()
        }

        // If dialog is already added to fragment manager, get it. If not, create a new instance.
        val iconDialog =
            supportFragmentManager.findFragmentByTag(Utils.ICON_DIALOG_TAG) as IconDialog?
                ?: IconDialog.newInstance(IconDialogSettings())
        binding.addCatIconBtn.setOnClickListener {
            // Open icon dialog
            iconDialog.show(supportFragmentManager, Utils.ICON_DIALOG_TAG)
        }

        // Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                val catName = binding.addCatName.text.toString()

                // Category name validation
                if (validateCatName(catName) == null) {

                    // Create color resource and icon R.drawable.id and pass them into our Category instance
                    if (pickedColor != null) {
                        catColor = pickedColor
                    }

                    // Use IconId = 0, unless the user chooses another Icon using Icon Picker
                    if (pickedIconId != null) {
                        catIconId = pickedIconId
                    }

                    // Sending the data back to the MainActivity
                    intent = Intent()
                    intent.putExtra(Utils.CAT_NAME_KEY, catName)
                    intent.putExtra(Utils.CAT_COLOR_KEY, catColor)
                    intent.putExtra(Utils.CAT_ICON_KEY, catIconId)

                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    // Display error
                    binding.addCatTxtLayoutName.helperText = validateCatName(catName)
                }
                true
            } else {
                false
            }
        }

    }


    //? Color Picker Functionality
    // Build and display the Color Picker Dialog
    private fun showColorPickerDialog() {
        SimpleColorDialog.build()
            .title(getString(R.string.icon_color))
            .colors(Utils.colorArray(this))
            .show(this, Utils.COLOR_PICK_TAG)
    }

    // This function allows us to record the chosen Color
    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {
        if (dialogTag == Utils.COLOR_PICK_TAG && which == SimpleDialog.OnDialogResultListener.BUTTON_POSITIVE) {
            pickedColor = extras.getInt(SimpleColorDialog.COLOR)
            binding.testCategory.setBackgroundColor(pickedColor!!)
            return true
        }
        return false
    }


    //? Input Validation functions
    // From using empty category name and the name with more than 20 symbols
    private fun validateCatName(catName: String): String? {
        if (catName == "" || catName.length > 20) {
            return "Required field"
        }
        return null
    }

    //? Show Alert Dialog when up button pressed
    //TODO: Add listener for back button (system button)
    private fun showExitAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Discard changes and exit?")
        builder.setMessage("This action cannot be undone.")
        builder.setNegativeButton("Keep editing") { dialog, i -> }
        builder.setPositiveButton("Yes") { dialog, i ->
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        builder.show()
    }

    //? Icon Dialog Functionality
    // Get Icon dialog pack from our Application
    override val iconDialogIconPack: IconPack?
        get() = (application as PersonalFinancesApplication).iconPack

    // Record the result of our Selected Icon
    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        // Show a toast with the list of selected icon IDs.
        val pickedIcon = icons[0]
        pickedIconId = pickedIcon.id
        binding.testCategory.icon = pickedIcon.drawable
        Toast.makeText(this, "Icon selected: $pickedIconId", Toast.LENGTH_SHORT).show()
    }
}