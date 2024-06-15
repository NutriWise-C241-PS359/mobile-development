package com.product.nutriwise.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.nutriwise.data.local.preference.UserModel
import com.product.nutriwise.data.local.preference.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    fun saveSession(userModel: UserModel){
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}