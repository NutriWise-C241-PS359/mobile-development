package com.product.nutriwise.di

import android.content.Context
import com.product.nutriwise.data.local.preference.UserPreference
import com.product.nutriwise.data.local.preference.UserRepository
import com.product.nutriwise.data.local.preference.dataStore

object Injection {
    fun providerUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}