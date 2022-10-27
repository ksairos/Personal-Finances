package com.example.personalfinances

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.databinding.ActivityMakeTransactionBinding
import com.example.personalfinances.models.MakeTransactionViewModel
import com.example.personalfinances.models.MakeTransactionViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MakeTransactionActivity : AppCompatActivity() {

    private var transactionRecipientId: Int? = null

    private lateinit var binding: ActivityMakeTransactionBinding

    // Calendar for a date picker
    private val myCalendar = Calendar.getInstance()

    // Initialize our database
    private val viewModel: MakeTransactionViewModel by viewModels {
        MakeTransactionViewModelFactory(
            (application as PersonalFinancesApplication).transactionsRepository,
            (application as PersonalFinancesApplication).accRepository,
            (application as PersonalFinancesApplication).catRepository
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the data from the intent
        transactionRecipientId = intent.getIntExtra(Utils.TRANSACTION_ID_TO_KEY, -1)

        // Set the name of recipient in the text field
        viewModel.getCatById(transactionRecipientId)
            .observe(this) { category ->
                binding.transactionTo.text = category.name
            }

        // Listener for a Cancel button
        binding.topAppBar.setNavigationOnClickListener {
            showExitAlertDialog()
        }

        // Use viewModel to fill categories for our Spinner
        viewModel.allAccountsNames.observe(this) { accounts ->
            ArrayAdapter(
                this@MakeTransactionActivity,
                android.R.layout.simple_spinner_item,
                accounts
            )
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.accountsSpinner.adapter = adapter
                }
        }

        // Choosing date by clicking on the button
        val currentDate = "${myCalendar.get(Calendar.DATE)}" +
                "/${(myCalendar.get(Calendar.MONTH) + 1)}" +
                "/${myCalendar.get(Calendar.YEAR)}"
        binding.createTransactionDateEditText.setText(currentDate)
        binding.createTransactionDateEditText.transformIntoDatePicker(this, "dd/mm/yyyy")


        binding.topAppBar.setOnMenuItemClickListener {

            val amountTxt = binding.createTransactionAmountEditText.text.toString()

            if (validateTransactionAmount(amountTxt) != null) {
                binding.createTransactionAmountLayout.helperText =
                    validateTransactionAmount(amountTxt)
                false
            } else {
                val amount = amountTxt.toDouble()
                // Use LiveData to find the chosen Account we want to use by its name
                viewModel.getAccByName(binding.accountsSpinner.selectedItem.toString())
                    .observe(this) { account_ ->
                        // Pass chosen account into our viewModel to create a transaction
                        viewModel.createTransaction(
                            account_,
                            transactionRecipientId,
                            amount,
                            binding.createTransactionDateEditText.text?.toString()
                        )

                        // Here we update the data in our account
                        viewModel.updateAcc(account_, amount)
                    }

                // And update the data in the chosen category
                viewModel.getCatById(transactionRecipientId)
                    .observe(this) { category ->
                        viewModel.updateCat(category, amount)
                    }

                finish()
                true
            }
        }
    }


    // Display an Alert dialog when pressing an Exit button
    private fun showExitAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Discard changes and exit?")
        builder.setMessage("This action cannot be undone.")
        builder.setNegativeButton("Keep editing") { dialog, i -> }
        builder.setPositiveButton("Yes") { dialog, i ->
            finish()
        }
        builder.show()
    }

    // Check if balance is not empty
    private fun validateTransactionAmount(amount: String): String? {
        if (amount == "") {
            return "Wrong input"
        }
        return null
    }

    // StackOverFlow's function for DatePicker
    private fun EditText.transformIntoDatePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }
}