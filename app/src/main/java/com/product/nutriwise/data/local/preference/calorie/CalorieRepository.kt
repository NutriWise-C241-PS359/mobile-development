package com.product.nutriwise.data.local.preference.calorie

import kotlinx.coroutines.flow.Flow

class CalorieRepository private constructor(
    private val caloriePreference: CaloriePreference
){

    suspend fun saveCalorie(calorieModel: CalorieModel) {
        caloriePreference.saveCalorie(calorieModel)
    }

    fun getCalorie(): Flow<CalorieModel> {
        return caloriePreference.getCalorie()
    }

    suspend fun clearCalorie() {
        caloriePreference.clearCalorie()
    }

    companion object {
        @Volatile
        private var instance: CalorieRepository? = null
        fun getInstance(
            caloriePreference: CaloriePreference
        ): CalorieRepository = synchronized(this){
            instance ?: CalorieRepository(caloriePreference)
        }.also { instance = it }
    }
}