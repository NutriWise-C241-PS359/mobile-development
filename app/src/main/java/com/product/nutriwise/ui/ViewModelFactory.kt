package com.product.nutriwise.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.product.nutriwise.data.local.preference.UserRepository
import com.product.nutriwise.di.Injection
import com.product.nutriwise.ui.login.LoginViewModel
import com.product.nutriwise.ui.main.home.HomeViewModel
import com.product.nutriwise.ui.main.profile.ProfileViewModel


class ViewModelFactory (
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> {
                ProfileViewModel(userRepository) as T
            }
           else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: createFactory(context).also { INSTANCE = it }
            }
        }

        private fun createFactory(context: Context): ViewModelFactory {
            val userRepository = Injection.providerUserRepository(context)
            return ViewModelFactory(userRepository)
        }
    }
}