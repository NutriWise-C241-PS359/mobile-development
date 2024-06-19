package com.product.nutriwise.ui.main.home.recomendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.local.preference.calorie.CalorieRepository
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.local.preference.user.UserRepository
import kotlinx.coroutines.launch

class RecomendationViewModel (private val calorieRepository: CalorieRepository,
    private val userRepository: UserRepository): ViewModel(){

    fun getUser(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getCalorie(): LiveData<CalorieModel> {
        return calorieRepository.getCalorie().asLiveData()
    }

    fun updateCalorieB(calorieModel: CalorieModel) {
        viewModelScope.launch {
            calorieRepository.updateCalorieB(calorieModel)
        }
    }

    fun updateCalorieL(calorieModel: CalorieModel) {
        viewModelScope.launch {
            calorieRepository.updateCalorieL(calorieModel)
        }
    }

    fun updateCalorieD(calorieModel: CalorieModel) {
        viewModelScope.launch {
            calorieRepository.updateCalorieD(calorieModel)
        }
    }
}