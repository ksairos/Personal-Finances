package com.example.personalfinances

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.personalfinances.databinding.ActivityAddAccountBinding

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
            setResult(RESULT_CANCELED, intent)
            finish()
        }

        // Listener for Confirmation button
        binding.addAccTopAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.toolbar_confirm) {

                // Collect data from Inputs
                val accName = binding.addAccName.text.toString()
                val accBalance = binding.addAccBalance.text.toString().toFloat()
                Log.d(TAG, "onCreate: My balance is $accBalance")

                // Account name validation
                if (validateAccName(accName) == null){

                    // For now we will use TextEdit to create Icon and Color of Accegory
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
                    intent.putExtra(Utils.ACC_BALANCE_KEY, accBalance)

                    setResult(RESULT_OK, intent)
                    finish()
                }else{
                    // Display error
                    binding.addAccTxtLayoutName.helperText = validateAccName(accName)
                }
                true
            } else {
                false
            }
        }

    }

    // From using empty Account name and the name with more than 20 symbols
    private fun validateAccName(accName: String): String? {
        if (accName == "" || accName.length > 20) {
            return "Required field"
        }
        return null
    }
}