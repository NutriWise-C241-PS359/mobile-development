package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("history")
	val history: List<HistoryItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class HistoryItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("cal")
	val cal: Double? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
