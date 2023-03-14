package com.example.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.data.BuildConfig
import com.example.data.Constants
import com.example.data.models.AuthResponse
import com.example.domain.repositories.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val httpClient: HttpClient,
) : AuthRepository {
    override fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { it[Constants.AccessTokenKey] != null }
    }

    override suspend fun getTokens(code: String): String {
        val response = httpClient.post(Constants.AccessTokenUrl) {
            parameter(Constants.CLIENT_ID_PARAM, BuildConfig.ghClientID)
            parameter(Constants.CLIENT_SECRET_PARAM, BuildConfig.ghClientSecret)
            parameter(Constants.CODE_PARAM, code)
            parameter(Constants.REDIRECT_URI_PARAM, BuildConfig.ghCallbackUrl)
        }
        if (response.status == HttpStatusCode.OK) {
            val data = response.body<AuthResponse>()
            dataStore.edit { prefs ->
                data.accessToken?.let {
                    prefs[Constants.AccessTokenKey] = it
                }
                data.refreshToken?.let {
                    prefs[Constants.RefreshTokenKey] = it
                }
            }
            return data.accessToken.orEmpty()
        } else {
            throw Exception("Unexpected error occurred! Please try again later.")
        }
    }
}
