package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryDetailResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("foodID")
	val foodID: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("food")
	val food: Food? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Food(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("energy")
	val energy: Double? = null
)
