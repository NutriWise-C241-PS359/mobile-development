package com.product.nutriwise.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.product.nutriwise.data.local.preference.profile.ProfileRepository
import com.product.nutriwise.data.local.preference.user.UserRepository
import com.product.nutriwise.di.Injection
import com.product.nutriwise.ui.login.LoginViewModel
import com.product.nutriwise.ui.main.home.HomeViewModel
import com.product.nutriwise.ui.main.profile.ProfileViewModel
import com.product.nutriwise.ui.signup.SignupViewModel
import com.product.nutriwise.ui.signup.inputProfile.InputProfileViewModel
import com.product.nutriwise.ui.splash.SplashViewModel


class ViewModelFactory (
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository , profileRepository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(InputProfileViewModel::class.java) -> {
                InputProfileViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository, profileRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> {
                ProfileViewModel(userRepository, profileRepository) as T
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
            val profileRepository = Injection.providerProfileRepository(context)
            return ViewModelFactory(userRepository, profileRepository)
        }
    }
}