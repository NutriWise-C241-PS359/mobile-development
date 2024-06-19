package com.product.nutriwise.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dateString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateString = date.format(formatter)

        binding.apply {
            btnLogin.setOnClickListener {
                val user = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()
                loginUser(user, password, dateString)
            }

            tvToSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
                finish()
            }
        }
    }

    private fun setActionBar() {
        supportActionBar?.hide()
    }
    private fun loginUser(user: String, password: String, date: String){
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.login(user, password)
                viewModel.saveSession(UserModel(user, response.user?.name.toString(), response.user?.token.toString(), true,false,date))
                val getProfileResponse = apiService.getUser(BR+response.user?.token.toString())
                viewModel.saveProfile(
                    ProfileModel(
                        getProfileResponse.profile?.usia ?: 0,
                        getProfileResponse.profile?.gender ?: false,
                        getProfileResponse.profile?.tinggibadan ?: 0.0,
                        getProfileResponse.profile?.beratbadan ?: 0.0,
                        getProfileResponse.profile?.aktivitas ?: 0
                    )
                )

                val responseCalCalorie = apiService.getHistoryPredict(BR+response.user?.token.toString())
                viewModel.saveCalorie(
                    CalorieModel(
                        responseCalCalorie.temp?.dailyCalories,
                        responseCalCalorie.temp?.calorieB,
                        responseCalCalorie.temp?.calorieL,
                        responseCalCalorie.temp?.calorieD,
                        responseCalCalorie.temp?.carbohydratesB,
                        responseCalCalorie.temp?.carbohydratesL,
                        responseCalCalorie.temp?.carbohydratesD,
                        responseCalCalorie.temp?.fatsB,
                        responseCalCalorie.temp?.fatsL,
                        responseCalCalorie.temp?.fatsD,
                        responseCalCalorie.temp?.proteinsB,
                        responseCalCalorie.temp?.proteinsL,
                        responseCalCalorie.temp?.proteinsD,
                        responseCalCalorie.temp?.addCalorieB,
                        responseCalCalorie.temp?.addCalorieL,
                        responseCalCalorie.temp?.addCalorieD
                    )
                )

                showToast(response.message.toString())
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
//                if (errorResponse.message.toString() == "No prediction data found for the user") {
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
//                }
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