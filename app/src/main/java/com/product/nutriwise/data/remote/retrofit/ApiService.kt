package com.product.nutriwise.data.remote.retrofit

import com.product.nutriwise.data.remote.response.AddTargetResponse
import com.product.nutriwise.data.remote.response.CalculateCalorieResponse
import com.product.nutriwise.data.remote.response.CalorieFromDBResponse
import com.product.nutriwise.data.remote.response.ErrorResponse
import com.product.nutriwise.data.remote.response.GetTargetByDateResponse
import com.product.nutriwise.data.remote.response.GetTargetResponse
import com.product.nutriwise.data.remote.response.HistoryDetailResponse
import com.product.nutriwise.data.remote.response.HistoryResponse
import com.product.nutriwise.data.remote.response.LoginResponse
import com.product.nutriwise.data.remote.response.RecommendResponse
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
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): ErrorResponse

    @FormUrlEncoded
    @PUT("updateUser")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Field("usia") usia: Int,
        @Field("gender") gender: Boolean,
        @Field("tinggibadan") tinggibandan: Double,
        @Field("beratbadan") beratbadan: Double,
        @Field("aktivitas") aktivitas: Int
    ): ErrorResponse

    @GET("users")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserResponse

    @FormUrlEncoded
    @PUT("updateName")
    suspend fun updateName(
        @Header("Authorization") token: String,
        @Field("name") name: String,
    ): ErrorResponse

    @GET("predictCal")
    suspend fun predictCal(
        @Header("Authorization") token: String
    ): CalculateCalorieResponse

    @GET("historyPredic")
    suspend fun getHistoryPredict(
        @Header("Authorization") token: String
    ): CalorieFromDBResponse

    @FormUrlEncoded
    @POST("recommend")
    suspend fun recommend(
        @Field("carbs") carbs: Double,
        @Field("protein") protein: Double,
        @Field("fats") fats: Double,
        @Field("calorie") calorie: Double,
        @Header("Authorization") token: String
    ): RecommendResponse

    @FormUrlEncoded
    @PUT("updatePredict")
    suspend fun updatePredict(
        @Field("dailyCalories") dailyCalories: Double?,
        @Field("calorieB") calorieB: Double?,
        @Field("calorieL") calorieL: Double?,
        @Field("calorieD") calorieD: Double?,
        @Field("carbohydratesB") carbohydratesB: Double?,
        @Field("carbohydratesL") carbohydratesL: Double?,
        @Field("carbohydratesD") carbohydratesD: Double?,
        @Field("fatsB") fatsB: Double?,
        @Field("fatsL") fatsL: Double?,
        @Field("fatsD") fatsD: Double?,
        @Field("proteinsB") proteinsB: Double?,
        @Field("proteinsL") proteinsL: Double?,
        @Field("proteinsD") proteinsD: Double?,
        @Field("addCalorieB") addCalorieB: Double?,
        @Field("addCalorieL") addCalorieL: Double?,
        @Field("addCalorieD") addCalorieD: Double?,
        @Header("Authorization") token: String
    ): ErrorResponse

    @FormUrlEncoded
    @POST("postFood")
    suspend fun addFoodHistory(
        @Field("foodID") foodID: Int,
        @Field("label") label: String,
        @Field("date") date: String,
        @Header("Authorization") token: String
    )

    @GET("history")
    suspend fun history(
        @Header("Authorization") token: String
    ) : HistoryResponse

    @GET("predict")
    suspend fun predict(
        @Header("Authorization") token: String
    ) : CalculateCalorieResponse

    @FormUrlEncoded
    @POST("historydetail")
    suspend fun historyDetail(
        @Header("Authorization") token: String,
        @Field("label") label: String,
        @Field("date") date: String,
    ): HistoryDetailResponse

    @FormUrlEncoded
    @PUT("updateTarget")//save targetnya
    suspend fun addTarget(
        @Header("Authorization") token: String,
        @Field("targetberatbadan") targetberatbadan: Int,
        @Field("duration") duration: Int
    ) : AddTargetResponse

    @GET("predictTarget")
    suspend fun getTarget(
        @Header("Authorization") token: String
    ) : GetTargetResponse

    @FormUrlEncoded
    @POST("historyTarget")
    suspend fun getTargetByDate(
        @Header("Authorization") token: String,
        @Field("date") date: String
    ): GetTargetByDateResponse
}