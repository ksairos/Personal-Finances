package com.example.personalfinances.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.personalfinances.databinding.FragmentAccountBottomSheetBinding

class AccountBottomSheetFragment : Fragment() {

    private lateinit var binding: FragmentAccountBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }
}