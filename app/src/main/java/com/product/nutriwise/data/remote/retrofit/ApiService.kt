package com.product.nutriwise.data.remote.retrofit

import com.product.nutriwise.data.remote.response.DicoLoginResponse
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded//dari cc
    @POST("login")
    suspend fun login1 (
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded//api dicoding
    @POST("login")
    suspend fun login (
        @Field("email") email: String,
        @Field("password") password: String
    ) : DicoLoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String
    ) : ErrorResponse
}