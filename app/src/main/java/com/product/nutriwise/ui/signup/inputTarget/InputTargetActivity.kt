package com.product.nutriwise.ui.signup.inputTarget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivityInputTargetBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.MainActivity
import com.product.nutriwise.ui.main.home.HomeFragment
import com.product.nutriwise.ui.splash.SplashViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class InputTargetActivity : AppCompatActivity() {

    private val viewModel by viewModels<InputTargetViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityInputTargetBinding
    private var durationInDays: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        val calendar = Calendar.getInstance()
        val today = calendar.timeInMillis
        val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today)
        calendar.add(Calendar.DAY_OF_YEAR, 14)
        val minDate = calendar.timeInMillis

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForwardCustom.from(minDate))

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        binding.layoutDate.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        binding.etDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        binding.tvSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
            binding.etDate.setText(datePicker.headerText)
            durationInDays = calculateDurationToDate(selectedDateInMillis).toInt()
        }

        binding.btnNext.setOnClickListener {
            val tbb = binding.etTb.text.toString().trim().toInt()
            Log.d("TAG -------------------------", "onCreate: $durationInDays")
            Log.d("TAG -------------------------", "onCreate: $tbb")
            viewModel.getSession().observe(this){
                val token = BR+it.token
                getPredictTarget(token, tbb, durationInDays, dateString)
            }
        }
    }

    private fun calculateDurationToDate(chosenDate: Long): Long {
        val todayInMillis = Calendar.getInstance().timeInMillis
        val diffInMillis = chosenDate - todayInMillis
        return diffInMillis / (1000 * 60 * 60 * 24)
    }

    private fun getPredictTarget(token: String, tbb: Int, duration: Int, dateString: String) {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                showLoading(false)
                apiService.addTarget(token, tbb, duration)
                apiService.getTarget(token)
                val response = apiService.getTargetByDate(token, dateString)
                viewModel.updateTarget(true)
                viewModel.saveCalorie(
                    CalorieModel(
                        response.temp?.dailyCalories,
                        response.temp?.calorieB,
                        response.temp?.calorieL,
                        response.temp?.calorieD,
                        response.temp?.carbohydratesB,
                        response.temp?.carbohydratesL,
                        response.temp?.carbohydratesD,
                        response.temp?.fatsB,
                        response.temp?.fatsL,
                        response.temp?.fatsD,
                        response.temp?.proteinsB,
                        response.temp?.proteinsL,
                        response.temp?.proteinsD,
                    )
                )
                startActivity(Intent(this@InputTargetActivity, MainActivity::class.java))
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
        AlertDialog.Builder(this)
            .setTitle(R.string.failed)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun setActionBar() {
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val BR = "Bearer "
    }
}

class DateValidatorPointForwardCustom(private val minDate: Long) : CalendarConstraints.DateValidator {

    override fun isValid(date: Long): Boolean {
        return date >= minDate
    }

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeLong(minDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : android.os.Parcelable.Creator<DateValidatorPointForwardCustom> {
        override fun createFromParcel(parcel: android.os.Parcel): DateValidatorPointForwardCustom {
            return DateValidatorPointForwardCustom(parcel.readLong())
        }

        override fun newArray(size: Int): Array<DateValidatorPointForwardCustom?> {
            return arrayOfNulls(size)
        }

        fun from(minDate: Long): DateValidatorPointForwardCustom {
            return DateValidatorPointForwardCustom(minDate)
        }

    }
}