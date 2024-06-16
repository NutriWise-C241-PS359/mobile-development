package com.product.nutriwise.di

import android.content.Context
import com.product.nutriwise.data.local.preference.user.UserPreference
import com.product.nutriwise.data.local.preference.user.UserRepository
import com.product.nutriwise.data.local.preference.user.dataStore

object Injection {
    fun providerUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}