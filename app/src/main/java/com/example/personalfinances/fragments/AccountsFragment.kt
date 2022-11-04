package com.example.personalfinances.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.Utils
import com.example.personalfinances.models.AccountViewModelFactory
import com.example.personalfinances.accounts.AccountsAdapter
import com.example.personalfinances.models.AccountsViewModel
import com.example.personalfinances.accounts.AddAccountActivity
import com.example.personalfinances.data.Account
import com.example.personalfinances.databinding.FragmentAccountsBinding

class AccountsFragment : Fragment() {

    private lateinit var adapter: AccountsAdapter
    private lateinit var binding: FragmentAccountsBinding
    private lateinit var newAccount: Account

    // Initialize ViewModel using PersonalFinancesApplication repository
    private val viewModel : AccountsViewModel by viewModels {
        AccountViewModelFactory((activity?.application as PersonalFinancesApplication).accRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsBinding.inflate(layoutInflater)

        // Set Recycler View
        binding.accountsRecView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AccountsAdapter(context)
        binding.accountsRecView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeData()
    }

    // this launcher allows us to get results from our another activity
    private var launcher: ActivityResultLauncher<Intent>? =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {

                    val accName = result.data?.getStringExtra(Utils.ACC_NAME_KEY)
                    val accColor = result.data?.getIntExtra(Utils.ACC_COLOR_KEY, 0)
                    val accIcon = result.data?.getIntExtra(Utils.ACC_ICON_KEY, 0)
                    val accBalance = result.data?.getDoubleExtra(Utils.ACC_BALANCE_KEY, 0.toDouble())

                    // The first added account becomes favorite
                    viewModel.allAccounts.observe(this){ accounts ->
                        newAccount = if (accounts.isEmpty()){
                            Account(null, accName, accBalance, true, accIcon, accColor)
                        }else{
                            Account(null, accName, accBalance, false, accIcon, accColor)
                        }
                    }
                    viewModel.insertAcc(newAccount)

                    Toast.makeText(requireActivity(), "A new account is added", Toast.LENGTH_SHORT).show()
                }
                AppCompatActivity.RESULT_CANCELED -> {
                    Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /*
This function is used to observe changes in our database. Whenever data is changed, the code in
the curly braces us run. In our case this updates the content in our adapter.
*/
    private fun observeData() {
        viewModel.allAccounts.observe(viewLifecycleOwner) { accounts ->
            adapter.submitList(accounts)
        }
    }

    // This function is used to initialize views and their inner content
    private fun init() {
        binding.apply {
            // Set Listener for FAB that creates new accounts
            addAccountFab.setOnClickListener {
                val intent = Intent(requireActivity(), AddAccountActivity::class.java)
                launcher?.launch(intent)
            }
        }
    }
}