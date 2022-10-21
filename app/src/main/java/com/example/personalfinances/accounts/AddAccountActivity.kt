package com.example.personalfinances.accounts

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.leanback.widget.Util
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.databinding.ActivityAddAccountBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class AddAccountActivity : AppCompatActivity() {

    private val TAG = "AddAccountActivity"
    private lateinit var binding: ActivityAddAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddAccountBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Arrays for Icon and Color resources
        val icons = resources.obtainTypedArray(R.array.icons)
        val colors = resources.obtainTypedArray(R.array.colors)

        // Listener for Cancel button
        binding.addAccTopAppBar.setNavigationOnClickListener {
            showExitAlertDialog()
        }

        // Listener for Confirmation button
        binding.addAccTopAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                val accName = binding.addAccName.text.toString()
                val accBalance = binding.addAccBalance.text.toString()
                // Account name validation
                if (validateAccName(accName) == null && validateAccBalance(accBalance) == null){

                    // For now we will use TextEdit to create Icon and Color of Category
                    // TODO: Create dialog for choosing colors and icons. Don't forget to add a default values
                    val accColorIdx = binding.addAccIcon.text.toString().toInt()
                    val accIconIdx = binding.addAccColor.text.toString().toInt()

                    // Create color resource and icon R.drawable.id and pass them into our Account instance
                    val accColor = colors.getColor(accColorIdx, -1)
                    val accIcon = icons.getResourceId(accIconIdx, -1)

                    // Sending the data back to the MainActivity
                    intent = Intent()
                    intent.putExtra(Utils.ACC_NAME_KEY, accName)
                    intent.putExtra(Utils.ACC_COLOR_KEY, accColor)
                    intent.putExtra(Utils.ACC_ICON_KEY, accIcon)
                    intent.putExtra(Utils.ACC_BALANCE_KEY, Utils.roundDouble(accBalance.toDouble()))

                    setResult(RESULT_OK, intent)
                    finish()
                }else{
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


    // Show AlertDialog when Exit button pressed
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