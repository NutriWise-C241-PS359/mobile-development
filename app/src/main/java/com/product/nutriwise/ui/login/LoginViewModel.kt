package com.product.nutriwise.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.product.nutriwise.data.local.preference.calorie.CalorieModel
import com.product.nutriwise.data.local.preference.calorie.CalorieRepository
import com.product.nutriwise.data.local.preference.profile.ProfileModel
import com.product.nutriwise.data.local.preference.profile.ProfileRepository
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.local.preference.user.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val calorieRepository: CalorieRepository
) : ViewModel() {
    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(userModel)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun saveProfile(profileModel: ProfileModel) {
        viewModelScope.launch {
            profileRepository.saveProfile(profileModel)
        }
    }

    fun saveCalorie(calorieModel: CalorieModel) {
        viewModelScope.launch {
            calorieRepository.saveCalorie(calorieModel)
        }
    }
}