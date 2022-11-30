package com.example.personalfinances.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.personalfinances.Utils
import com.example.personalfinances.accounts.EditAccountActivity
import com.example.personalfinances.databinding.FragmentAccountBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAccountBottomSheetBinding
    private var accId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        accId = arguments?.getInt(Utils.ACC_ID_TAG)
        binding = FragmentAccountBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        const val TAG = "AccountBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.editCategoryBtn.setOnClickListener {
            Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(), EditAccountActivity::class.java)
            intent.putExtra(Utils.ACC_ID_TAG, accId)
            dismiss()
            startActivity(intent)
        }

        binding.transferCategoryBtn.setOnClickListener {
            Toast.makeText(context, "Transfer Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.deleteCategoryBtn.setOnClickListener {
            Toast.makeText(context, "Delete Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}