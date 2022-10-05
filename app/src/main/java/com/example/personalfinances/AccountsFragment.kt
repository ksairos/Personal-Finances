package com.example.personalfinances

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personalfinances.data.Account
import com.example.personalfinances.data.Category
import com.example.personalfinances.data.MainDb
import com.example.personalfinances.databinding.FragmentAccountsBinding
import kotlinx.coroutines.launch

class AccountsFragment : Fragment() {

    private lateinit var adapter: AccountAdapter
    private lateinit var binding: FragmentAccountsBinding
    private val accDb by lazy { MainDb.getDb(requireContext()).accDao() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsBinding.inflate(layoutInflater)

        // Set Recycler View
        binding.accountsRecView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AccountAdapter()
        binding.accountsRecView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeCatDb()
    }

    // this launcher allows us to get results from our another activity
    private var launcher: ActivityResultLauncher<Intent>? =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {

                    val accName = result.data?.getStringExtra(Utils.ACC_NAME_KEY)
                    val accColor = result.data?.getIntExtra(Utils.ACC_COLOR_KEY, 0)
                    val accIcon = result.data?.getIntExtra(Utils.ACC_ICON_KEY, 0)
                    val accBalance = result.data?.getFloatExtra(Utils.ACC_BALANCE_KEY, 0.toFloat())

                    val newAccount = Account(null, accName, accBalance, false, accIcon, accColor)

                    lifecycleScope.launch {
                        accDb.insert(newAccount)
                    }
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
    private fun observeCatDb() {
        lifecycleScope.launch {
            accDb.getAll().collect { accountList ->
                if (accountList.isNotEmpty()) {
                    adapter.submitList(accountList)
                }
            }
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