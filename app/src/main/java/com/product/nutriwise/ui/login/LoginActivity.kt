package com.product.nutriwise.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.profile.ProfileModel
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivityLoginBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.MainActivity
import com.product.nutriwise.ui.signup.SignupActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                val user = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()
                loginUser(user, password)
            }

            tvToSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
                finish()
            }
        }
    }

    private fun loginUser(user: String, password: String){
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.login(user, password)
                showToast(response.message.toString())
                viewModel.saveSession(UserModel(user, response.user?.name.toString(), response.user?.token.toString()))
                val getProfileResponse = apiService.getUser(BR+response.user?.token.toString())
                viewModel.saveProfile(
                    ProfileModel(
                        getProfileResponse.profile?.usia ?: 0,
                        getProfileResponse.profile?.gender ?: false,
                        getProfileResponse.profile?.tinggibadan ?: 0,
                        getProfileResponse.profile?.beratbadan ?: 0,
                        getProfileResponse.profile?.aktivitas ?: 0
                    )
                )
                showLoading(false)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }catch (e: HttpException) {
                showLoading(false)
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
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
        const val BR = "Bearer "
    }
}