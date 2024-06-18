package com.product.nutriwise.data.local.preference.calorie

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "calorie")

class CaloriePreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveCalorie(calorieModel: CalorieModel) {
        dataStore.edit { preferences ->
            preferences[Keys.DAILY_CALORIES] = calorieModel.dailyCalories ?: 0.0
            preferences[Keys.CALORIES_B] = calorieModel.caloriesB ?: 0.0
            preferences[Keys.CALORIES_L] = calorieModel.caloriesL ?: 0.0
            preferences[Keys.CALORIES_D] = calorieModel.caloriesD ?: 0.0
            preferences[Keys.CARBOHYDRATES_B] = calorieModel.carbohydratesB ?: 0.0
            preferences[Keys.CARBOHYDRATES_L] = calorieModel.carbohydratesL ?: 0.0
            preferences[Keys.CARBOHYDRATES_D] = calorieModel.carbohydratesD ?: 0.0
            preferences[Keys.FATS_B] = calorieModel.fatsB ?: 0.0
            preferences[Keys.FATS_L] = calorieModel.fatsL ?: 0.0
            preferences[Keys.FATS_D] = calorieModel.fatsD ?: 0.0
            preferences[Keys.PROTEINS_B] = calorieModel.proteinsB ?: 0.0
            preferences[Keys.PROTEINS_L] = calorieModel.proteinsL ?: 0.0
            preferences[Keys.PROTEINS_D] = calorieModel.proteinsD ?: 0.0
        }
    }

    suspend fun updateCalorieB(calorieModel: CalorieModel) {
        dataStore.edit { preferences ->
            preferences[Keys.CARBOHYDRATES_B] = calorieModel.carbohydratesB ?: 0.0
            preferences[Keys.FATS_B] = calorieModel.fatsB ?: 0.0
            preferences[Keys.PROTEINS_B] = calorieModel.proteinsB ?: 0.0
            preferences[Keys.ADDCALORIE_B] = calorieModel.addCalorieB ?: 0.0
        }
    }

    suspend fun updateCalorieL(calorieModel: CalorieModel) {
        dataStore.edit { preferences ->
            preferences[Keys.CARBOHYDRATES_L] = calorieModel.carbohydratesL ?: 0.0
            preferences[Keys.FATS_L] = calorieModel.fatsL ?: 0.0
            preferences[Keys.PROTEINS_L] = calorieModel.proteinsL ?: 0.0
            preferences[Keys.ADDCALORIE_L] = calorieModel.addCalorieL ?: 0.0
        }
    }

    suspend fun updateCalorieD(calorieModel: CalorieModel) {
        dataStore.edit { preferences ->
            preferences[Keys.CARBOHYDRATES_D] = calorieModel.carbohydratesD ?: 0.0
            preferences[Keys.FATS_D] = calorieModel.fatsD ?: 0.0
            preferences[Keys.PROTEINS_D] = calorieModel.proteinsD ?: 0.0
            preferences[Keys.ADDCALORIE_D] = calorieModel.addCalorieD ?: 0.0
        }
    }

    fun getCalorie(): Flow<CalorieModel> {
        return dataStore.data.map { preferences ->
            CalorieModel(
                dailyCalories = preferences[Keys.DAILY_CALORIES],
                caloriesB = preferences[Keys.CALORIES_B],
                caloriesL = preferences[Keys.CALORIES_L],
                caloriesD = preferences[Keys.CALORIES_D],
                carbohydratesB = preferences[Keys.CARBOHYDRATES_B],
                carbohydratesL = preferences[Keys.CARBOHYDRATES_L],
                carbohydratesD = preferences[Keys.CARBOHYDRATES_D],
                fatsB = preferences[Keys.FATS_B],
                fatsL = preferences[Keys.FATS_L],
                fatsD = preferences[Keys.FATS_D],
                proteinsB = preferences[Keys.PROTEINS_B],
                proteinsL = preferences[Keys.PROTEINS_L],
                proteinsD = preferences[Keys.PROTEINS_D],
                addCalorieB = preferences[Keys.ADDCALORIE_B],
                addCalorieL = preferences[Keys.ADDCALORIE_L],
                addCalorieD = preferences[Keys.ADDCALORIE_D]
            )
        }
    }

    suspend fun clearCalorie() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: CaloriePreference? = null

        private object Keys {
            val DAILY_CALORIES = doublePreferencesKey("daily_calories")
            val CALORIES_B = doublePreferencesKey("calories_b")
            val CALORIES_L = doublePreferencesKey("calories_l")
            val CALORIES_D = doublePreferencesKey("calories_d")
            val CARBOHYDRATES_B = doublePreferencesKey("carbohydrates_b")
            val CARBOHYDRATES_L = doublePreferencesKey("carbohydrates_l")
            val CARBOHYDRATES_D = doublePreferencesKey("carbohydrates_d")
            val FATS_B = doublePreferencesKey("fats_b")
            val FATS_L = doublePreferencesKey("fats_l")
            val FATS_D = doublePreferencesKey("fats_d")
            val PROTEINS_B = doublePreferencesKey("proteins_b")
            val PROTEINS_L = doublePreferencesKey("proteins_l")
            val PROTEINS_D = doublePreferencesKey("proteins_d")
            val ADDCALORIE_B = doublePreferencesKey("addcalorie_b")
            val ADDCALORIE_L = doublePreferencesKey("addcalorie_l")
            val ADDCALORIE_D = doublePreferencesKey("addcalorie_d")
        }

        fun getIntance(dataStore: DataStore<Preferences>): CaloriePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = CaloriePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

    }
}
