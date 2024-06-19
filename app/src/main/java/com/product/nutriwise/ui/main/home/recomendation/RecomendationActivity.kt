package com.product.nutriwise.ui.main.home.recomendation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.product.nutriwise.R
import com.product.nutriwise.adapter.ListRecomendationAdapter
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.response.ResultItem
import com.product.nutriwise.data.remote.retrofit.ApiConfig
import com.product.nutriwise.databinding.ActivityRecomendationBinding
import com.product.nutriwise.ui.ViewModelFactory
import com.product.nutriwise.ui.main.MainActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max

class RecomendationActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecomendationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRecomendationBinding
    private lateinit var rvRecomendation: RecyclerView
    private val list = ArrayList<ResultItem>()
    private lateinit var token: String
    private lateinit var dateString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateString = date.format(formatter)
        rvRecomendation = binding.rvRecomendation
        rvRecomendation.setHasFixedSize(true)

        val addFood = intent.getIntExtra("ADD_FOOD_KEY", 1)
        viewModel.getUser().observe(this){
            token =  BR+it.token
        }
        viewModel.getCalorie().observe(this) {
            when (addFood) {
                1 -> {
                    val calorie = max(0.0, (it.caloriesB ?: 0.0) - (it.addCalorieB ?: 0.0))
                    getListRecomendation(
                        it.carbohydratesB ?: 0.0,
                        it.proteinsB ?: 0.0,
                        it.fatsB ?: 0.0,
                        calorie,
                        token

                    )
                }

                2 -> {
                    val calorie = max(0.0, (it.caloriesL ?: 0.0) - (it.addCalorieL ?: 0.0))
                    getListRecomendation(
                        it.carbohydratesL ?: 0.0,
                        it.proteinsL ?: 0.0,
                        it.fatsL ?: 0.0,
                        calorie,
                        token
                    )
                }

                else -> {
                    val calorie = max(0.0, (it.caloriesD ?: 0.0) - (it.addCalorieD ?: 0.0))
                    getListRecomendation(
                        it.carbohydratesD ?: 0.0,
                        it.proteinsD ?: 0.0,
                        it.fatsD ?: 0.0,
                        calorie,
                        token
                    )
                }
            }

        }
        supportActionBar?.setTitle("Recommendation")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbRecommendation.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerList() {
        showLoading(false)
        val addFood = intent.getIntExtra("ADD_FOOD_KEY", 1)
        Log.d("TAGTESRECO>>>>>>>>>>>>>>>>>>>", "onItemClicked: $addFood")
        rvRecomendation.layoutManager = LinearLayoutManager(this)
        val listRecomendationAdapter = ListRecomendationAdapter(list)
        rvRecomendation.adapter = listRecomendationAdapter

        listRecomendationAdapter.setOnItemClickCallback(object :
            ListRecomendationAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultItem) {
                val addressUri = Uri.parse("geo:0,0?q=${data.name}")
                val mapIntent = Intent(Intent.ACTION_VIEW, addressUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        })

        listRecomendationAdapter.setOnButtonClickCallback(object :
            ListRecomendationAdapter.OnButtonClickCallback {
            override fun onItemClicked(data: ResultItem) {
                val addCal = intent.getDoubleExtra("addCal", 0.0)
                val carbs = max(0.0,intent.getDoubleExtra("car", 0.0) - data.carbohydrates.toString().toDouble())
                val fats = max(0.0, intent.getDoubleExtra("fat", 0.0) - data.fats.toString().toDouble())
                val protein = max(0.0, intent.getDoubleExtra("pro", 0.0) - data.protein.toString().toDouble())
                var label : String
                when (addFood) {
                    1 -> {
                        viewModel.updateCalorieB(
                            CalorieModel(
                                addCalorieB = addCal + data.calorie.toString().toDouble(),
                                carbohydratesB = carbs,
                                fatsB = fats,
                                proteinsB = protein
                            )
                        )
                        label = "breakfast"
                    }
                    2 -> {
                        viewModel.updateCalorieL(
                            CalorieModel(
                                addCalorieL = addCal + data.calorie.toString().toDouble(),
                                carbohydratesL = carbs,
                                fatsL = fats,
                                proteinsL = protein
                            )
                        )
                        label = "lunch"
                    }
                    else -> {
                        viewModel.updateCalorieD(
                            CalorieModel(
                                addCalorieD = addCal + data.calorie.toString().toDouble(),
                                carbohydratesD = carbs,
                                fatsD = fats,
                                proteinsD = protein
                            )
                        )
                        label = "diner"
                    }
                }

                val apiService = ApiConfig.getApiService()
                lifecycleScope.launch {
                    apiService.addFoodHistory(data.id.toString().toInt(), label, dateString, token)
                    try {
                        val intent = Intent(this@RecomendationActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: HttpException) {
                        val errorBody = e.response()?.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        showErrorDialog(errorResponse.message.toString())
                    }
                }

            }
        })
    }

    private fun getListRecomendation(
        carbs: Double,
        protein: Double,
        fats: Double,
        calorie: Double,
        token: String
    ) {
        showLoading(true)
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.recommend(carbs, protein, fats, calorie, token)
                list.clear()
                response.result?.let { list.addAll(it.filterNotNull()) }
                showRecyclerList()

            } catch (e: HttpException) {
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

    companion object {
        const val BR = "Bearer "
    }
}