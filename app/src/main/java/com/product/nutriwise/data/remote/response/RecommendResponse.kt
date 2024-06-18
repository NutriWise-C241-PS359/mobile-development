package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendResponse(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ResultItem(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("calorie")
	val calorie: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
