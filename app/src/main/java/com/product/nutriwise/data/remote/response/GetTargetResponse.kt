package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetTargetResponse(

	@field:SerializedName("currentCalories")
	val currentCalories: Double? = null,

	@field:SerializedName("dailyCalorieChange")
	val dailyCalorieChange: Double? = null,

	@field:SerializedName("macronutrientsList")
	val macronutrientsList: List<MacronutrientsListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("targetCalories")
	val targetCalories: Double? = null
)

data class Dinner1(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("proteins")
	val proteins: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)

data class Lunch1(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("proteins")
	val proteins: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)

data class Breakfast1(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("proteins")
	val proteins: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)

data class MacronutrientsListItem(

	@field:SerializedName("lunch")
	val lunch: Lunch1? = null,

	@field:SerializedName("dailyCalories")
	val dailyCalories: Double? = null,

	@field:SerializedName("breakfast")
	val breakfast: Breakfast1? = null,

	@field:SerializedName("day")
	val day: String? = null,

	@field:SerializedName("dinner")
	val dinner: Dinner1? = null
)
