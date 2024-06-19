package com.product.nutriwise.ui.main.history.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.local.preference.user.UserRepository

class HistoryDetailViewModel(private val userRepository: UserRepository) : ViewModel(){

    fun getSession(): LiveData<UserModel>{
        return userRepository.getSession().asLiveData()
    }
}