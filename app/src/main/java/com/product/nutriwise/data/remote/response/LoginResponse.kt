package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("users")
	val users: Users? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Users(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
