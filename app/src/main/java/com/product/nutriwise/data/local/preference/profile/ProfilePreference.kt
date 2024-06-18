package com.product.nutriwise.data.local.preference.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class ProfilePreference private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveProfile(profileModel: ProfileModel) {
        dataStore.edit {
            it[UMUR_KEY] = profileModel.umur
            it[GENDER_KEY] = profileModel.gender
            it[TINGGIBADAN_KEY] = profileModel.tinggibadan
            it[BERATBADAN_KEY] = profileModel.beratbadan
            it[AKTIVITAS_KEY] = profileModel.aktivitas
        }
    }

    suspend fun updateProfile(updatedProfile: ProfileModel) {
        dataStore.edit { preferences ->
            preferences[UMUR_KEY] = updatedProfile.umur
            preferences[GENDER_KEY] = updatedProfile.gender
            preferences[TINGGIBADAN_KEY] = updatedProfile.tinggibadan
            preferences[BERATBADAN_KEY] = updatedProfile.beratbadan
            preferences[AKTIVITAS_KEY] = updatedProfile.aktivitas
        }
    }

    fun getProfile(): Flow<ProfileModel> {
        return dataStore.data.map {
            ProfileModel(
                it[UMUR_KEY] ?: 0,
                it[GENDER_KEY] ?: false,
                it[TINGGIBADAN_KEY] ?: 0.0,
                it[BERATBADAN_KEY] ?: 0.0,
                it[AKTIVITAS_KEY] ?: 0
            )
        }
    }

    suspend fun clearProfile() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ProfilePreference? = null

        private val UMUR_KEY = intPreferencesKey("umur")
        private val GENDER_KEY = booleanPreferencesKey("gender")
        private val TINGGIBADAN_KEY = doublePreferencesKey("tinggibadan")
        private val BERATBADAN_KEY = doublePreferencesKey("beratbadan")
        private val AKTIVITAS_KEY = intPreferencesKey("aktifitas")

        fun getInstance(dataStore: DataStore<Preferences>): ProfilePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ProfilePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}