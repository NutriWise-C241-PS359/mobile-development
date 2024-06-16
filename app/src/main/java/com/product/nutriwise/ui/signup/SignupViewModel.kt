package com.product.nutriwise.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.nutriwise.data.local.preference.user.UserModel
import com.product.nutriwise.data.local.preference.user.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel (private val repository: UserRepository): ViewModel() {
    fun saveSession(userModel: UserModel){
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}