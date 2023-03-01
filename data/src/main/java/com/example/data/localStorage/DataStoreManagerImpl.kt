package com.example.data.localStorage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.domain.localStorage.DataStoreManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val USER_ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

class DataStoreManagerImpl(private val dataStore: DataStore<Preferences>) : DataStoreManager {
    override fun getUserAccessToken() =
        dataStore.data
            .catch {
                emit(emptyPreferences())
            }.map { preferences ->
                preferences[USER_ACCESS_TOKEN_KEY].orEmpty()
            }

    override suspend fun saveUserAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[USER_ACCESS_TOKEN_KEY] = accessToken
        }
    }
}