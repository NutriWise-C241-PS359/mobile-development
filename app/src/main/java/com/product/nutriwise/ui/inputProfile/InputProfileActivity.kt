package com.product.nutriwise.ui.inputProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.product.nutriwise.R
import com.product.nutriwise.databinding.ActivityInputProfileBinding
import com.product.nutriwise.ui.inputTarget.InputTargetActivity

class InputProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genderList = listOf("Laki-laki", "Perempuan")
        val adapterGender = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderList)
        binding.dbGender.setAdapter(adapterGender)

        val activityList = listOf("1", "2", "3", "4", "5")
        val adapterActivity = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, activityList)
        binding.dbActivity.setAdapter(adapterActivity)

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, InputTargetActivity::class.java))
            finish()
        }
    }
}