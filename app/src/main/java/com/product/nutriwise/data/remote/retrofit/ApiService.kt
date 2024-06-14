package com.product.nutriwise.data.remote.retrofit

import com.product.nutriwise.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login (
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

}