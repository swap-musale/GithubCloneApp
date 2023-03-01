package com.example.domain.localStorage

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    fun getUserAccessToken(): Flow<String>
    suspend fun saveUserAccessToken(accessToken: String)
}