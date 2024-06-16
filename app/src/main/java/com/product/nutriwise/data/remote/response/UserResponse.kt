package com.product.nutriwise.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val profile: Profile? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Profile(

	@field:SerializedName("usia")
	val usia: Int? = 0,

	@field:SerializedName("tinggibadan")
	val tinggibadan: Int? = 0,

	@field:SerializedName("gender")
	val gender: Boolean? = false,

	@field:SerializedName("beratbadan")
	val beratbadan: Int? = 0,

	@field:SerializedName("name")
	val name: String? = "",

	@field:SerializedName("username")
	val username: String? = "",

	@field:SerializedName("aktivitas")
	val aktivitas: Int? = 0
)
