package com.product.nutriwise.data.local.preference.profile

import kotlinx.coroutines.flow.Flow

class ProfileRepository private constructor(
    private val profilePreference: ProfilePreference
){
    suspend fun saveProfile(profileModel: ProfileModel) {
        profilePreference.saveProfile(profileModel)
    }

    suspend fun updateProfile(profileModel: ProfileModel) {
        profilePreference.updateProfile(profileModel)
    }

    fun getProfile(): Flow<ProfileModel> {
        return profilePreference.getProfile()
    }

    suspend fun clearProfile() {
        profilePreference.clearProfile()
    }

    companion object{
        @Volatile
        private var instance: ProfileRepository? = null
        fun getInstance(
            profilePreference: ProfilePreference
        ): ProfileRepository = synchronized(this){
            instance ?: ProfileRepository(profilePreference)
        }.also { instance = it }
    }
}