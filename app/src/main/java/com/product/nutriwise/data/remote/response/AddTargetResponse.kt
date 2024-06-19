package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddTargetResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("target")
	val target: Target? = null
)

data class Target(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("targetberatbadan")
	val targetberatbadan: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
