package com.product.nutriwise.data.local.preference.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val  Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class ProfilePreference private constructor(private val dataStore: DataStore<Preferences>){

}