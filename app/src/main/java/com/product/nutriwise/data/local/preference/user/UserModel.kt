package com.product.nutriwise.data.local.preference.user

class UserModel(
    val username: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)