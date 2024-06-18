package com.product.nutriwise.ui.main.home.recomendation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import kotlin.math.max

class RecomendationActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecomendationViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRecomendationBinding
    private lateinit var rvRecomendation: RecyclerView
    private val list = ArrayList<ResultItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvRecomendation = binding.rvRecomendation
        rvRecomendation.setHasFixedSize(true)

        val addFood = intent.getIntExtra("ADD_FOOD_KEY", 1)

        viewModel.getCalorie().observe(this) {
            when (addFood) {
                1 -> {
                    val calorie = max(0.0, (it.caloriesB ?: 0.0) - (it.addCalorieB ?: 0.0))
                    getListRecomendation(
                        it.carbohydratesB ?: 0.0,
                        it.proteinsB ?: 0.0,
                        it.fatsB ?: 0.0,
                        calorie

                    )
                }

                2 -> {
                    val calorie = max(0.0, (it.caloriesL ?: 0.0) - (it.addCalorieL ?: 0.0))
                    getListRecomendation(
                        it.carbohydratesL ?: 0.0,
                        it.proteinsL ?: 0.0,
                        it.fatsL ?: 0.0,
                        calorie
                    )
                }

                else -> {
                    val calorie = max(0.0, (it.caloriesD ?: 0.0) - (it.addCalorieD ?: 0.0))
                    getListRecomendation(
                        it.carbohydratesD ?: 0.0,
                        it.proteinsD ?: 0.0,
                        it.fatsD ?: 0.0,
                        calorie
                    )
                }
            }

        }
        supportActionBar?.setTitle("Recomendation")
    }

    private fun showRecyclerList() {
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
                    }
                }
                val intent = Intent(this@RecomendationActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun getListRecomendation(
        carbs: Double,
        protein: Double,
        fats: Double,
        calorie: Double
    ) {
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.recommend(carbs, protein, fats, calorie)
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
}