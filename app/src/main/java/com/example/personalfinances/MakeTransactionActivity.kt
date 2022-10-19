package com.example.personalfinances

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinances.data.Account
import com.example.personalfinances.databinding.ActivityMakeTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class MakeTransactionActivity : AppCompatActivity() {

    private var transactionRecipientId: Int? = null

//    private lateinit var account: Account

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


        // TODO: Set the name of the recipient
//        binding.transactionTo.text = transactionRecipientName

        // Listener for a Cancel button
        binding.topAppBar.setNavigationOnClickListener {
            // TODO: Add alert dialog for canceling transaction
            finish()
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


        // TODO: Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener {

            // Use LiveData to find the chosen Account we want to use by its name
            viewModel.getAccByName(binding.accountsSpinner.selectedItem.toString())
                .observe(this) { account_ ->
                    // Pass chosen account into our viewModel
                    viewModel.createTransactionToCategory(
                        account_,
                        transactionRecipientId,
                        binding.createTransactionAmountEditText.text.toString().toDouble(),
                        binding.createTransactionDateEditText.text?.toString()
                    )
                }
            finish()
            true
        }
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