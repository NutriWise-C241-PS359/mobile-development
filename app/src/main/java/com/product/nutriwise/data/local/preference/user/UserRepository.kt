package com.product.nutriwise.data.local.preference.user

import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    suspend fun saveSession(userModel: UserModel) {
        userPreference.saveSession(userModel)
    }

    suspend fun updateName(newName: String) {
        userPreference.updateName(newName)
    }

    suspend fun updateTarget(newTarget: Boolean){
        userPreference.updateTarget(newTarget)
    }

    suspend fun updateDate(newDate: String) {
        userPreference.updateDate(newDate)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}