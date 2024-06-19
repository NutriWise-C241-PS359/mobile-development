package com.product.nutriwise.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class HomeViewModel(
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

    fun getCalorie(): LiveData<CalorieModel> {
        return calorieRepository.getCalorie().asLiveData()
    }

    fun saveCalorie(calorieModel: CalorieModel) {
        viewModelScope.launch {
            calorieRepository.saveCalorie(calorieModel)
        }
    }

    fun updateDate(newDate: String){
        viewModelScope.launch {
            userRepository.updateDate(newDate)
        }
    }

}