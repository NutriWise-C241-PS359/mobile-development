package com.product.nutriwise.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.product.nutriwise.data.local.preference.calorie.CalorieRepository
import com.product.nutriwise.data.local.preference.profile.ProfileModel
import com.product.nutriwise.data.local.preference.profile.ProfileRepository
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.local.preference.user.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val calorieRepository: CalorieRepository
) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getProfile(): LiveData<ProfileModel> {
        return profileRepository.getProfile().asLiveData()
    }

    suspend fun updateName(newName: String) {
        userRepository.updateName(newName)
    }

    fun clearProfile() {
        viewModelScope.launch {
            profileRepository.clearProfile()
        }
    }

    fun clearCalorie() {
        viewModelScope.launch {
            calorieRepository.clearCalorie()
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}