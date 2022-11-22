package com.example.personalfinances.categories

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.data.Account
import com.example.personalfinances.data.Category
import com.example.personalfinances.databinding.ActivityEditCategoryBinding
import com.example.personalfinances.models.CategoriesViewModel
import com.example.personalfinances.models.CategoriesViewModelFactory
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog

class EditCategoryActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener,
IconDialog.Callback {

    private lateinit var binding: ActivityEditCategoryBinding

    private var pickedColor: Int? = null
    private var pickedIconId: Int? = null
    private var catId: Int? = null

    private lateinit var updatedCategory: Category

    // Initialize ViewModel using PersonalFinancesApplication repository
    private val viewModel: CategoriesViewModel by viewModels {
        CategoriesViewModelFactory((this.application as PersonalFinancesApplication).catRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        //? Set initial account data

        //! Default values for the account Color and Icon
        catId = intent.getIntExtra(Utils.CAT_ID_TAG, -1)
        var catColor: Int? = null
        var catIconId: Int? = null
        var catName: String
        var catExpenses: String = "0"

        // Get Account data using Account ID
        viewModel.getCatById(catId).observe(this) { category_ ->
            catColor = category_.color
            catIconId = category_.icon
            catName = category_.name.toString()
            catExpenses = category_.expenses.toString()

            // Set initial data to views
            catColor?.let { binding.testEditCategory.setBackgroundColor(it) }
            binding.testEditCategory.icon = catIconId?.let {
                PersonalFinancesApplication.instance.iconPack?.getIcon(
                    it
                )?.drawable
            }
            binding.editCatName.setText(catName)
        }


        //? Initialize Color picker
        binding.editCatColorBtn.setOnClickListener {
            Utils.showColorPickerDialog(this, this)
        }

        //? If dialog is already added to fragment manager, get it. If not, create a new instance.
        val iconDialog =
            supportFragmentManager.findFragmentByTag(Utils.ICON_DIALOG_TAG) as IconDialog?
                ?: IconDialog.newInstance(IconDialogSettings())
        binding.editCatIconBtn.setOnClickListener {
            // Open icon dialog
            iconDialog.show(supportFragmentManager, Utils.ICON_DIALOG_TAG)
        }

        //? Listener for Confirmation button
        binding.editCatTopAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                catName = binding.editCatName.text.toString()
                // Account name validation
                if (Utils.validateName(catName) == null) {

                    // Create color resource and icon R.drawable.id and pass them into our Account instance
                    if (pickedColor != null) {
                        catColor = pickedColor
                    }

                    // Use IconId = 0, unless the user chooses another Icon using Icon Picker
                    if (pickedIconId != null) {
                        catIconId = pickedIconId
                    }

                    viewModel.allCats.observe(this) {
                        updatedCategory = Category(
                            catId,
                            catName,
                            Utils.roundDouble(catExpenses.toDouble()),
                            catIconId,
                            catColor
                        )
                        viewModel.updateCat(updatedCategory)
                    }

                    setResult(AppCompatActivity.RESULT_OK, intent)
                    finish()
                } else {
                    // Display error
                    binding.editCatTxtLayoutName.helperText = Utils.validateName(catName)
                }
                true
            } else {
                false
            }
        }

        //? Listener for Cancel button
        binding.editCatTopAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    //? This function allows us to record the chosen Color
    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {
        if (dialogTag == Utils.COLOR_PICK_TAG && which == SimpleDialog.OnDialogResultListener.BUTTON_POSITIVE) {
            pickedColor = extras.getInt(SimpleColorDialog.COLOR)
            binding.testEditCategory.setBackgroundColor(pickedColor!!)
            return true
        }
        return false
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
        binding.testEditCategory.icon = pickedIcon.drawable
        Toast.makeText(this, "Icon selected: $pickedIconId", Toast.LENGTH_SHORT).show()
    }


    //? Show Alert Dialog when up button pressed
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Discard changes and exit?")
        builder.setMessage("This action cannot be undone.")
        builder.setNegativeButton("Keep editing") { dialog, i -> }
        builder.setPositiveButton("Yes") { dialog, i ->
            setResult(AppCompatActivity.RESULT_CANCELED, intent)
            super.onBackPressed()
        }
        builder.show()
    }

}