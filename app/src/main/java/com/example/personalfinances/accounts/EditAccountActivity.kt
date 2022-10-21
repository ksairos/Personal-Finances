package com.example.personalfinances.accounts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personalfinances.databinding.ActivityEditAccountBinding

class EditAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}