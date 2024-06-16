package com.product.nutriwise.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivitySignupBinding
import com.product.nutriwise.ui.login.LoginActivity
import com.product.nutriwise.ui.main.MainActivity
import com.product.nutriwise.ui.signup.inputProfile.InputProfileActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSignup.setOnClickListener {
                val name = etName.text.toString().trim()
                val user = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                register(name, user, password)
            }

            tvToLogin.setOnClickListener {
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun register(name: String, user: String, password: String){
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.register(name, user, password)
                showToast(response.message.toString())
                val intent = Intent(this@SignupActivity, InputProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }
    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    companion object{
        val TAG = "SignupActivityLOG"
    }
}