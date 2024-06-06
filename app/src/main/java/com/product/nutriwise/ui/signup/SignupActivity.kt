package com.product.nutriwise.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.product.nutriwise.R
import com.product.nutriwise.databinding.ActivitySignupBinding
import com.product.nutriwise.ui.inputProfile.InputProfileActivity
import com.product.nutriwise.ui.inputTarget.InputTargetActivity
import com.product.nutriwise.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, InputTargetActivity::class.java))
            finish()
        }
    }
}