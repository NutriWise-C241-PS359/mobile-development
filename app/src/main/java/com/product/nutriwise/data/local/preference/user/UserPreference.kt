package com.product.nutriwise.data.local.preference.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(userModel: UserModel) {
        dataStore.edit {
            it[USERNAME_KEY] = userModel.username
            it[NAME_KEY] = userModel.name
            it[TOKEN_KEY] = userModel.token
            it[IS_LOGIN_KEY] = true
            it[DATE_KEY] = userModel.date
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[USERNAME_KEY] ?: "",
                it[NAME_KEY] ?: "",
                it[TOKEN_KEY] ?: "",
                it[IS_LOGIN_KEY] ?: false,
                it[TARGET_KEY] ?: false,
                it[DATE_KEY] ?: ""
            )
        }
    }

    suspend fun updateName(newName: String) {
        dataStore.edit {
            it[NAME_KEY] = newName
        }
    }

    suspend fun updateTarget(newTarget: Boolean){
        dataStore.edit {
            it[TARGET_KEY] = newTarget
        }
    }

    suspend fun updateDate(newDate: String) {
        dataStore.edit {
            it[DATE_KEY] = newDate
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERNAME_KEY = stringPreferencesKey("username")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val DATE_KEY = stringPreferencesKey("date")
        private val TARGET_KEY = booleanPreferencesKey("target")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

    }
}