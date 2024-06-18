package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class CalculateCalorieResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Lunch(

	@field:SerializedName("macronutrients")
	val macronutrients: Macronutrients? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)

data class Result(

	@field:SerializedName("lunch")
	val lunch: Lunch? = null,

	@field:SerializedName("dailyCalories")
	val dailyCalories: Double? = null,

	@field:SerializedName("breakfast")
	val breakfast: Breakfast? = null,

	@field:SerializedName("dinner")
	val dinner: Dinner? = null
)

data class Macronutrients(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("proteins")
	val proteins: Double? = null
)

data class Dinner(

	@field:SerializedName("macronutrients")
	val macronutrients: Macronutrients? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)

data class Breakfast(

	@field:SerializedName("macronutrients")
	val macronutrients: Macronutrients? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)
