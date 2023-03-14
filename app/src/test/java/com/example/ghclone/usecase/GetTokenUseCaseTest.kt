package com.example.ghclone.usecase

import com.example.domain.repositories.AuthRepository
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.utils.Resource
import com.example.ghclone.repository.FakeAuthRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTokenUseCaseTest {

    lateinit var authRepository: AuthRepository
    lateinit var getTokenUseCase: GetTokenUseCase

    @Test
    fun ifAccessTokenNotEmptyReturnsTrue() = runBlocking {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = true)
        getTokenUseCase = GetTokenUseCase(authRepository = authRepository)
        val result = getTokenUseCase("code")
        if (result is Resource.Success) {
            assertThat(result.data).isEqualTo("fakeAccessToken")
        }
    }

    @Test
    fun ifAccessTokenNotEmptyReturnsFalse() = runBlocking {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = false)
        getTokenUseCase = GetTokenUseCase(authRepository = authRepository)
        val result = getTokenUseCase("code")
        if (result is Resource.Success) {
            assertThat(result.data).isEqualTo("Something went wrong")
        }
    }
}
