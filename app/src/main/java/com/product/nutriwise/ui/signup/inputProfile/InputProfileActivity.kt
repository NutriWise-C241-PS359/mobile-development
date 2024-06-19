package com.product.nutriwise.ui.signup.inputProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.local.preference.profile.ProfileModel
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivityInputProfileBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.MainActivity
import com.product.nutriwise.ui.signup.inputTarget.InputTargetActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<InputProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityInputProfileBinding
    private var value: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        val genderMap = mapOf(
            "Laki-laki" to true,
            "Perempuan" to false
        )

        val genderList = genderMap.keys.toList()
        val adapterGender =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderList)
        binding.dbGender.setAdapter(adapterGender)

        val activityMap = mapOf(
            "sangat jarang berolahraga" to 1,
            "jarang berolahraga (1-3 kali perminggu)" to 2,
            "cukup berolahraga (3-5 kali perminggu)" to 3,
            "sering berolahraga (6-7 kali perminggu)" to 4,
            "sangat sering berolahraga (2 kali sehari)" to 5
        )

        val activityList = activityMap.keys.toList()
        val adapterActivity =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, activityList)
        binding.dbActivity.setAdapter(adapterActivity)
        value = intent.getIntExtra(EK, 0)
        binding.apply {
            if (value == 1) {
                viewModel.getProfile().observe(this@InputProfileActivity) {
                    etBb.setText(it.beratbadan.toString())
                    etTb.setText(it.tinggibadan.toString())
                    etUmur.setText(it.umur.toString())
//                    if (it.gender == true) {
//                        dbGender.setText("Laki-laki")
//                    } else {
//                        dbGender.setText("perempuan")
//                    }
//                    dbActivity.setText(activityList[it.aktivitas - 1]) // Adjusted to set correct activity
                }
            }
            btnNext.setOnClickListener {
                val usia = etUmur.text.toString().trim()
                val gender = dbGender.text.toString().trim()
                val tinggibandan = etTb.text.toString().trim().toDouble()
                val beratbadan = etBb.text.toString().trim().toDouble()
                val aktivitas = dbActivity.text.toString().trim()

                val genderValue = genderMap[gender] ?: false
                val activityValue = activityMap[aktivitas] ?: 1
                viewModel.getSession().observe(this@InputProfileActivity) {
                    val token = BR + it.token
                    updateProfile(
                        token,
                        usia.toInt(),
                        genderValue,
                        tinggibandan,
                        beratbadan,
                        activityValue
                    )
                }
            }
        }
    }

    private fun updateProfile(
        token: String,
        usia: Int,
        gender: Boolean,
        tinggibadan: Double,
        beratbadan: Double,
        aktivitas: Int
    ) {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                apiService.updateUser(token, usia, gender, tinggibadan, beratbadan, aktivitas)
                val responseCalCalorie = apiService.predictCal(token)
                viewModel.saveCalorie(
                    CalorieModel(
                        responseCalCalorie.result?.dailyCalories,
                        responseCalCalorie.result?.breakfast?.calories,
                        responseCalCalorie.result?.lunch?.calories,
                        responseCalCalorie.result?.dinner?.calories,
                        responseCalCalorie.result?.breakfast?.macronutrients?.carbohydrates,
                        responseCalCalorie.result?.lunch?.macronutrients?.carbohydrates,
                        responseCalCalorie.result?.dinner?.macronutrients?.carbohydrates,
                        responseCalCalorie.result?.breakfast?.macronutrients?.fats,
                        responseCalCalorie.result?.lunch?.macronutrients?.fats,
                        responseCalCalorie.result?.dinner?.macronutrients?.fats,
                        responseCalCalorie.result?.breakfast?.macronutrients?.proteins,
                        responseCalCalorie.result?.lunch?.macronutrients?.proteins,
                        responseCalCalorie.result?.dinner?.macronutrients?.proteins,
                    )
                )
                showLoading(false)
                Log.d("TAG", "updateProfile: $value")
                if (value == 0) {
                    viewModel.saveProfile(
                        ProfileModel(
                            usia,
                            gender,
                            tinggibadan,
                            beratbadan,
                            aktivitas
                        )
                    )
                    val intent = Intent(this@InputProfileActivity, InputTargetActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    viewModel.updateProfile(
                        ProfileModel(
                            usia,
                            gender,
                            tinggibadan,
                            beratbadan,
                            aktivitas
                        )
                    )
                    viewModel.updateTarget(false)
                    val intent = Intent(this@InputProfileActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
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
        const val BR = "Bearer "
        const val EK = "EDIT_KEY"
    }
}