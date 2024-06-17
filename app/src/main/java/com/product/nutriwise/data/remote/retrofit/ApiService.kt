package com.product.nutriwise.data.remote.retrofit

import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.response.LoginResponse
import com.product.nutriwise.data.remote.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login (
        @Field("username") username: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String
    ) : ErrorResponse

    @FormUrlEncoded
    @PUT("updateUser")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("usia") usia: Int,
        @Field("gender") gender: Boolean,
        @Field("tinggibadan") tinggibandan: Double,
        @Field("beratbadan") beratbadan: Double,
        @Field("aktivitas") aktivitas: Int
    ) : ErrorResponse

    @GET("users")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserResponse

    @FormUrlEncoded
    @PUT("updateName")
    suspend fun updateName(
        @Header("Authorization") token: String,
        @Field("name") name: String,
    ) : ErrorResponse
}