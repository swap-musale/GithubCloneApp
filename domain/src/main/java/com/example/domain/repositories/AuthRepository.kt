package com.example.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isLoggedIn(): Flow<Boolean>
    suspend fun getTokens(code: String)
    suspend fun logout()
}
