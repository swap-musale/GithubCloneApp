package com.example.ghclone.repository

import com.example.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAuthRepositoryImpl(val shouldSucceed: Boolean) : AuthRepository {

    override fun isLoggedIn(): Flow<Boolean> {
        return flow { emit(value = shouldSucceed) }
    }

    override suspend fun getTokens(code: String): String {
        return if (shouldSucceed) {
            "fakeAccessToken"
        } else {
            "Something went wrong"
        }
    }
}
