package com.example.personalfinances

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.personalfinances.data.MainDb
import com.example.personalfinances.databinding.ActivityMakeTransactionBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MakeTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeTransactionBinding
    // Calendar for a date picker
    private val myCalendar = Calendar.getInstance()
    // Initialize our database
    private val viewModel: MakeTransactionViewModel by viewModels {
        MakeTransactionViewModelFactory((application as PersonalFinancesApplication).transactionsRepository,
            (application as PersonalFinancesApplication).accRepository,
            (application as PersonalFinancesApplication).catRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener for a Cancel button
        binding.topAppBar.setNavigationOnClickListener {
            // TODO: Add alert dialog for canceling transaction
            finish()
        }

        // TODO: Listener for Confirmation button
        binding.topAppBar.setOnMenuItemClickListener { true }

            viewModel.allAccountsNames.observe(this) { accounts ->
                ArrayAdapter(this@MakeTransactionActivity, android.R.layout.simple_spinner_item, accounts)
                    .also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.accountsSpinner.adapter = adapter
                    }
            }

        binding.accountsSpinner
        // Set the name of the recipient
        val transactionRecipient = intent.getIntExtra(Utils.TRANSACTION_TO_KEY, -1).toString()
        binding.transactionTo.text = transactionRecipient

        // Choosing date by clicking on the button
        val currentDate = "${myCalendar.get(Calendar.DATE)}" +
                "/${(myCalendar.get(Calendar.MONTH)+1)}" +
                "/${myCalendar.get(Calendar.YEAR)}"
        binding.createTransactionDateEditText.setText(currentDate)

        binding.createTransactionDateEditText.transformIntoDatePicker(this, "dd/mm/yyyy")

    }


    // StackOverFlow's function for DatePicker
    private fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
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