package com.example.ghclone.usecase

import com.example.domain.repositories.AuthRepository
import com.example.domain.usecases.GetIsLoggedInUseCase
import com.example.ghclone.repository.FakeAuthRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetIsLoggedInUseCaseTest {

    lateinit var authRepository: AuthRepository
    lateinit var getIsLoggedInUseCase: GetIsLoggedInUseCase

    @Test
    fun isUserLoggedInReturnTrue() = runTest {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = true)
        getIsLoggedInUseCase = GetIsLoggedInUseCase(authRepository = authRepository)
        val result = getIsLoggedInUseCase().first()
        assertThat(result).isTrue()
    }

    @Test
    fun isUserNotLoggedInReturnFalse() = runTest {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = false)
        getIsLoggedInUseCase = GetIsLoggedInUseCase(authRepository = authRepository)
        val result = getIsLoggedInUseCase().first()
        assertThat(result).isFalse()
    }
}
