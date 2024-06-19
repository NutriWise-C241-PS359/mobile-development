package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class CalorieFromDBResponse(

	@field:SerializedName("temp")
	val temp: Temp? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Temp(

	@field:SerializedName("carbohydratesL")
	val carbohydratesL: Double? = null,

	@field:SerializedName("proteinsL")
	val proteinsL: Double? = null,

	@field:SerializedName("fatsD")
	val fatsD: Double? = null,

	@field:SerializedName("fatsB")
	val fatsB: Double? = null,

	@field:SerializedName("addCalorieD")
	val addCalorieD: Double? = null,

	@field:SerializedName("carbohydratesD")
	val carbohydratesD: Double? = null,

	@field:SerializedName("proteinsD")
	val proteinsD: Double? = null,

	@field:SerializedName("carbohydratesB")
	val carbohydratesB: Double? = null,

	@field:SerializedName("addCalorieB")
	val addCalorieB: Double? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("proteinsB")
	val proteinsB: Double? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("calorieL")
	val calorieL: Double? = null,

	@field:SerializedName("dailyCalories")
	val dailyCalories: Double? = null,

	@field:SerializedName("calorieB")
	val calorieB: Double? = null,

	@field:SerializedName("fatsL")
	val fatsL: Double? = null,

	@field:SerializedName("calorieD")
	val calorieD: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("addCalorieL")
	val addCalorieL: Double? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
