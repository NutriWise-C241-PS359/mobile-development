package com.product.nutriwise.di

import android.content.Context
import com.product.nutriwise.data.local.preference.calorie.CaloriePreference
import com.product.nutriwise.data.local.preference.calorie.CalorieRepository
import com.product.nutriwise.data.local.preference.profile.ProfilePreference
import com.product.nutriwise.data.local.preference.profile.ProfileRepository
import com.product.nutriwise.data.local.preference.user.UserPreference
import com.product.nutriwise.data.local.preference.user.UserRepository
import com.product.nutriwise.data.local.preference.user.dataStore

object Injection {
    fun providerUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun providerProfileRepository(context: Context): ProfileRepository {
        val pref = ProfilePreference.getInstance(context.dataStore)
        return ProfileRepository.getInstance(pref)
    }

    fun providerCalorieRepository(context: Context): CalorieRepository {
        val pref = CaloriePreference.getIntance(context.dataStore)
        return CalorieRepository.getInstance(pref)
    }
}