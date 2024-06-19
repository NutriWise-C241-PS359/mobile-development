package com.product.nutriwise.ui.signup.inputTarget

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.local.preference.calorie.CalorieRepository
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.local.preference.user.UserRepository

class InputTargetViewModel(private val userRepository: UserRepository,
    private val calorieRepository: CalorieRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    suspend fun updateTarget(newTarget: Boolean){
        userRepository.updateTarget(newTarget)
    }

    suspend fun saveCalorie(calorieModel: CalorieModel){
        calorieRepository.saveCalorie(calorieModel)
    }
}