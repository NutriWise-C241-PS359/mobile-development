package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetTargetByDateResponse(

	@field:SerializedName("temp")
	val temp: Temp? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
