package com.product.nutriwise.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.product.nutriwise.data.local.preference.UserModel
import com.product.nutriwise.data.local.preference.UserRepository

class HomeViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}