package com.example.personalfinances.accounts

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.data.Account
import com.example.personalfinances.databinding.ActivityEditAccountBinding
import com.example.personalfinances.models.AccountViewModelFactory
import com.example.personalfinances.models.AccountsViewModel
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog

class EditAccountActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener,
    IconDialog.Callback {

    private lateinit var binding: ActivityEditAccountBinding

    private var pickedColor: Int? = null
    private var pickedIconId: Int? = null
    private var accId: Int? = null

    private lateinit var updatedAccount: Account

    // Initialize ViewModel using PersonalFinancesApplication repository
    private val viewModel: AccountsViewModel by viewModels {
        AccountViewModelFactory((this.application as PersonalFinancesApplication).accRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        //? Set initial account data

        //! Default values for the account Color and Icon
        accId = intent.getIntExtra(Utils.ACC_ID_TAG, -1)
        var accFavorite = false
        var accColor: Int? = null
        var accIconId: Int? = null
        var accName: String
        var accBalance: String

        // Get Account data using Account ID
        viewModel.getAccById(accId).observe(this) { account_ ->
            accFavorite = account_.favorite
            accColor = account_.color
            accIconId = account_.icon
            accName = account_.name.toString()
            accBalance = account_.balance.toString()

            // Set initial data to views
            accColor?.let { binding.testEditAccount.setBackgroundColor(it) }
            binding.testEditAccount.icon = accIconId?.let {
                PersonalFinancesApplication.instance.iconPack?.getIcon(
                    it
                )?.drawable
            }
            binding.editAccName.setText(accName)
            binding.editAccBalance.setText(accBalance)
        }


        //? Initialize Color picker
        binding.editAccColorBtn.setOnClickListener {
            Utils.showColorPickerDialog(this, this)
        }

        //? If dialog is already added to fragment manager, get it. If not, create a new instance.
        val iconDialog =
            supportFragmentManager.findFragmentByTag(Utils.ICON_DIALOG_TAG) as IconDialog?
                ?: IconDialog.newInstance(IconDialogSettings())
        binding.editAccIconBtn.setOnClickListener {
            // Open icon dialog
            iconDialog.show(supportFragmentManager, Utils.ICON_DIALOG_TAG)
        }

        //? Listener for Confirmation button
        binding.editAccTopAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                accName = binding.editAccName.text.toString()
                accBalance = binding.editAccBalance.text.toString()
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

                    viewModel.allAccounts.observe(this) {
                        updatedAccount = Account(
                            accId,
                            accName,
                            Utils.roundDouble(accBalance.toDouble()),
                            accFavorite,
                            accIconId,
                            accColor
                        )
                        viewModel.updateAcc(updatedAccount)
                    }

                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    // Display error
                    binding.editAccTxtLayoutName.helperText = validateAccName(accName)
                    binding.editAccTxtInpLayoutBalance.helperText = validateAccBalance(accBalance)
                }
                true
            } else {
                false
            }
        }
    }

    //? This function allows us to record the chosen Color
    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {
        if (dialogTag == Utils.COLOR_PICK_TAG && which == SimpleDialog.OnDialogResultListener.BUTTON_POSITIVE) {
            pickedColor = extras.getInt(SimpleColorDialog.COLOR)
            binding.testEditAccount.setBackgroundColor(pickedColor!!)
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
        binding.testEditAccount.icon = pickedIcon.drawable
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