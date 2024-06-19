package com.product.nutriwise.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.FragmentHomeBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.home.recomendation.RecomendationActivity
import com.product.nutriwise.ui.signup.SignupActivity
import com.product.nutriwise.ui.signup.inputProfile.InputProfileActivity
import com.product.nutriwise.utils.Utils
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var maxCalorie: Double? = null
    private var proCalorie: Double? = null
    private val binding get() = _binding!!
    private lateinit var dateString: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

        showLoading(true)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory
            .getInstance(requireContext()))[HomeViewModel::class.java]

        binding.apply {
            showLoading(false)

            viewModel.getSession().observe(viewLifecycleOwner) {
                tvName.text = "Hi, ${it.name}"
                val date = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                dateString = date.format(formatter)
                val formatter2 = Utils.convertDateFormat(dateString)
                if (it.isTarget){
                    tvTitleTarget.setText("Target tanggal $formatter2")
                    tvTitleTarget.visibility = view.visibility
                }
                if (dateString != it.date){
                    if (it.isTarget){
                        getCalorieTarget(it.token)
                    }else{
                        newCalculateCalorie(it.token)
                    }
                }
            }

            viewModel.getProfile().observe(viewLifecycleOwner) {
                if (it.beratbadan == 0.0) {
                    showErrorDialogP("Belum isi data profile")
                }
                val bmi = Utils.calculateBMI(it.beratbadan, it.tinggibadan)
                val categoryBMI = Utils.getBMICategory(bmi, it.gender)

                tvTitleBmi2.text = String.format(" %.2f ", bmi)
                tvTitleBmi3.text = "($categoryBMI)"
            }

            viewModel.getCalorie().observe(viewLifecycleOwner) {
                maxCalorie = it.dailyCalories
                proCalorie =
                    (it.addCalorieB ?: 0.0) + (it.addCalorieL ?: 0.0) + (it.addCalorieD ?: 0.0)
                tvNumOfCal.text = String.format("%.2f/${maxCalorie?.toInt()}", proCalorie)
                piCalorie.max = maxCalorie?.toInt() ?: 0
                piCalorie.progress = proCalorie?.toInt() ?: 0

                //Breakfast
                needcalBreakfast2.text = String.format(
                    " %.2f gram ",
                    max(0.0, (it.caloriesB ?: 0.0) - (it.addCalorieB ?: 0.0))
                )
                fillcalBreakfast2.text = String.format(" %.2f gram ", it.addCalorieB ?: 0.0)
                addfoodBreakfast.setOnClickListener { _ ->
                    val tmp = it.addCalorieB ?: 0.0
                    val intent = Intent(requireContext(), RecomendationActivity::class.java)
                    intent.putExtra("ADD_FOOD_KEY", 1)
                    intent.putExtra("addCal", tmp)
                    intent.putExtra("cal", it.caloriesB)
                    intent.putExtra("car", it.carbohydratesB)
                    intent.putExtra("fat", it.fatsB)
                    intent.putExtra("pro", it.proteinsB)
                    startActivity(intent)
                }
                //Lunch
                needcalLunch2.text = String.format(
                    " %.2f gram ",
                    max(0.0, (it.caloriesL ?: 0.0) - (it.addCalorieL ?: 0.0))
                )
                fillcalLunch2.text = String.format(" %.2f gram ", it.addCalorieL ?: 0.0)
                addfoodLunch.setOnClickListener { _ ->
                    val tmp = it.addCalorieL ?: 0.0
                    val intent = Intent(requireContext(), RecomendationActivity::class.java)
                    intent.putExtra("ADD_FOOD_KEY", 2)
                    intent.putExtra("addCal", tmp)
                    intent.putExtra("cal", it.caloriesL)
                    intent.putExtra("car", it.carbohydratesL)
                    intent.putExtra("fat", it.fatsL)
                    intent.putExtra("pro", it.proteinsL)
                    startActivity(intent)
                }

                //Diner
                needcalDinner2.text = String.format(
                    " %.2f gram ",
                    max(0.0, (it.caloriesD ?: 0.0) - (it.addCalorieD ?: 0.0))
                )
                fillcalDinner2.text = String.format(" %.2f gram ", it.addCalorieD ?: 0.0)
                addfoodDinner.setOnClickListener { _ ->
                    val tmp = it.addCalorieD ?: 0.0
                    val intent = Intent(requireContext(), RecomendationActivity::class.java)
                    intent.putExtra("ADD_FOOD_KEY", 3)
                    intent.putExtra("addCal", tmp)
                    intent.putExtra("cal", it.caloriesD)
                    intent.putExtra("car", it.carbohydratesD)
                    intent.putExtra("fat", it.fatsD)
                    intent.putExtra("pro", it.proteinsD)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getCalorieTarget(token: String) {
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.getTargetByDate(BR+token, dateString)
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
            }catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                if (errorResponse.status == "error"){
                    binding.tvTitleTarget.visibility = View.GONE
                    newCalculateCalorie(BR+token)
                }
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }

    private fun newCalculateCalorie(token: String) {
        showToast(dateString)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val responseCalCalorie = apiService.predict(BR+token)
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
                viewModel.updateDate(dateString)
            } catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                showErrorDialog(errorResponse.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.failed)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showErrorDialogP(message: String) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.failed).setMessage(message)
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(requireContext(), InputProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(SignupActivity.EK, 0)
                startActivity(intent)
            }
            .setCancelable(false)
            .show()
    }

    private fun showToast(message: String){
        Toast.makeText(requireContext(), "Today $message", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val BR = "Bearer "
    }
}
