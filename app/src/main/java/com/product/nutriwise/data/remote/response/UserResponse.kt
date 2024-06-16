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
	val usia: Int? = null,

	@field:SerializedName("tinggibadan")
	val tinggibadan: Double? = null,

	@field:SerializedName("gender")
	val gender: Boolean? = null,

	@field:SerializedName("beratbadan")
	val beratbadan: Double? = null,

	@field:SerializedName("name")
	val name: String? = "",

	@field:SerializedName("username")
	val username: String? = "",

	@field:SerializedName("aktivitas")
	val aktivitas: Int? = 0
)
