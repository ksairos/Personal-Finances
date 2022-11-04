package com.example.personalfinances.accounts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.databinding.ActivityAddAccountBinding
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog

class AddAccountActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener,
    IconDialog.Callback {

    private val TAG = "AddAccountActivity"
    private lateinit var binding: ActivityAddAccountBinding

    private var pickedColor: Int? = null
    private var pickedIconId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Default values for the account Color and Icon
        var accColor: Int? = Utils.colorArray(this)[0]
        var accIconId: Int? = 0

        // Set default color and icon to the test icon
        accColor?.let { binding.testAccount.setBackgroundColor(it) }
        binding.testAccount.icon = accIconId?.let {
            PersonalFinancesApplication.instance.iconPack?.getIcon(
                it
            )?.drawable
        }

        // Listener for Cancel button
        binding.addAccTopAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Listener for Color Pick button
        binding.addAccColorBtn.setOnClickListener {
            showColorPickerDialog()
        }

        // If dialog is already added to fragment manager, get it. If not, create a new instance.
        val iconDialog =
            supportFragmentManager.findFragmentByTag(Utils.ICON_DIALOG_TAG) as IconDialog?
                ?: IconDialog.newInstance(IconDialogSettings())
        binding.addAccIconBtn.setOnClickListener {
            // Open icon dialog
            iconDialog.show(supportFragmentManager, Utils.ICON_DIALOG_TAG)
        }

        // Listener for Confirmation button
        binding.addAccTopAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                val accName = binding.addAccName.text.toString()
                val accBalance = binding.addAccBalance.text.toString()
                // Account name validation
                if (validateAccName(accName) == null && validateAccBalance(accBalance) == null) {

                    // Create color resource and icon R.drawable.id and pass them into our Account instance
                    if (pickedColor != null) {
                        accColor = pickedColor
                    }

                    // Use IconId = 0, unless the user chooses another Icon using Icon Picker
                    if (pickedIconId != null) {
                        accIconId = pickedIconId
                    }

                    // Sending the data back to the MainActivity
                    intent = Intent()
                    intent.putExtra(Utils.ACC_NAME_KEY, accName)
                    intent.putExtra(Utils.ACC_COLOR_KEY, accColor)
                    intent.putExtra(Utils.ACC_ICON_KEY, accIconId)
                    intent.putExtra(Utils.ACC_BALANCE_KEY, Utils.roundDouble(accBalance.toDouble()))

                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    // Display error
                    binding.addAccTxtLayoutName.helperText = validateAccName(accName)
                    binding.addAccTxtInpLayoutBalance.helperText = validateAccBalance(accBalance)
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
            binding.testAccount.setBackgroundColor(pickedColor!!)
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
        binding.testAccount.icon = pickedIcon.drawable
        Toast.makeText(this, "Icon selected: $pickedIconId", Toast.LENGTH_SHORT).show()
    }


    //? Show Alert Dialog when up button pressed
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Discard changes and exit?")
        builder.setMessage("This action cannot be undone.")
        builder.setNegativeButton("Keep editing") { dialog, i -> }
        builder.setPositiveButton("Yes") { dialog, i ->
            setResult(RESULT_CANCELED, intent)
            super.onBackPressed()
        }
        builder.show()
    }

    //? Input Validation functions
    // From using empty Account name and the name with more than 20 symbols
    private fun validateAccName(accName: String): String? {
        if (accName == "" || accName.length > 20) {
            return "Wrong input"
        }
        return null
    }

    // From using empty Balance input
    private fun validateAccBalance(account_balance: String): String? {
        if (account_balance == "") {
            return "Wrong input"
        }
        return null
    }
}