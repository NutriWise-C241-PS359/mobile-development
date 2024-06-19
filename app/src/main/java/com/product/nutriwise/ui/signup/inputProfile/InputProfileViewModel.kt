package com.product.nutriwise.ui.signup.inputProfile

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

class InputProfileViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val calorieRepository: CalorieRepository
) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    suspend fun updateTarget(newTarget: Boolean){
        userRepository.updateTarget(newTarget)
    }
    fun saveProfile(profileModel: ProfileModel) {
        viewModelScope.launch {
            profileRepository.saveProfile(profileModel)
        }
    }

    fun getProfile(): LiveData<ProfileModel> {
        return profileRepository.getProfile().asLiveData()
    }

    fun updateProfile(profileModel: ProfileModel) {
        viewModelScope.launch {
            profileRepository.updateProfile(profileModel)
        }
    }

    fun saveCalorie(calorieModel: CalorieModel) {
        viewModelScope.launch {
            calorieRepository.saveCalorie(calorieModel)
        }
    }
}