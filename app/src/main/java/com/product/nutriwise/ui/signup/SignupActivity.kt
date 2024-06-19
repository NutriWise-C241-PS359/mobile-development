package com.product.nutriwise.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivitySignupBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.login.LoginActivity
import com.product.nutriwise.ui.signup.inputProfile.InputProfileActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignupBinding
    private lateinit var dateString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateString = date.format(formatter)

        binding.apply {
            btnSignup.setOnClickListener {
                val name = etName.text.toString().trim()
                val user = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                register(name, user, password, dateString)
            }

            tvToLogin.setOnClickListener {
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun register(name: String, user: String, password: String, date: String) {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                apiService.register(name, user, password)
                showLoading(false)
                val responseLogin = apiService.login(user, password)
                viewModel.saveSession(UserModel(user, name, responseLogin.user?.token.toString(),true,false,date))
                val intent = Intent(this@SignupActivity, InputProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(EK, 0)
                startActivity(intent)
                finish()
            } catch (e: HttpException) {
                showLoading(false)
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setActionBar() {
        supportActionBar?.hide()
    }

    companion object {
        const val EK = "EDIT_KEY"
    }
}