package com.example.personalfinances

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personalfinances.databinding.ActivityAccountsBinding

class AccountsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAccountsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}