package com.example.ghclone.viewmodel

import app.cash.turbine.test
import com.example.domain.repositories.AuthRepository
import com.example.domain.usecases.GetIsLoggedInUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.ghclone.repository.FakeAuthRepositoryImpl
import com.example.ghclone.ui.auth.AuthViewModel
import com.example.ghclone.utils.UIState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeAuthViewModel {

    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModelUnderTest: AuthViewModel
    private val coroutineTestDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
    }

    @Test
    fun onAppLaunch_CheckUserLoggedIn_ReturnsTrue() = runTest {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = true)
        val getIsLoggedInUseCase = GetIsLoggedInUseCase(authRepository = authRepository)
        val getTokenUseCase = GetTokenUseCase(authRepository = authRepository)

        authViewModelUnderTest = AuthViewModel(
            coroutineDispatcher = coroutineTestDispatcher,
            getIsLoggedInUseCase = getIsLoggedInUseCase,
            getTokensUseCase = getTokenUseCase,
        )

        authViewModelUnderTest.isLoggedIn.test {
            awaitItem() // Here we're using awaitItem() 2 times as flow initial value is null and updated value was not getting into test block
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun onAppLaunch_CheckUserLoggedIn_ReturnsFalse() = runTest {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = false)
        val getIsLoggedInUseCase = GetIsLoggedInUseCase(authRepository = authRepository)
        val getTokenUseCase = GetTokenUseCase(authRepository = authRepository)

        authViewModelUnderTest = AuthViewModel(
            coroutineDispatcher = coroutineTestDispatcher,
            getIsLoggedInUseCase = getIsLoggedInUseCase,
            getTokensUseCase = getTokenUseCase,
        )

        authViewModelUnderTest.isLoggedIn.test {
            awaitItem()
            assertThat(awaitItem()).isFalse()
        }
    }

    @Test
    fun signInWithButtonClick_callAPI_ReturnsTrue() = runTest {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = true)
        val getIsLoggedInUseCase = GetIsLoggedInUseCase(authRepository = authRepository)
        val getTokenUseCase = GetTokenUseCase(authRepository = authRepository)

        authViewModelUnderTest = AuthViewModel(
            coroutineDispatcher = coroutineTestDispatcher,
            getIsLoggedInUseCase = getIsLoggedInUseCase,
            getTokensUseCase = getTokenUseCase,
        )

        authViewModelUnderTest.authenticate(code = "code")
        authViewModelUnderTest.authState.test {
            val result = awaitItem()
            if (result is UIState.Success) {
                assertThat(result.data).isEqualTo("fakeAccessToken")
            }
        }
    }

    @Test
    fun signInWithButtonClick_callAPI_ReturnsFalse() = runTest {
        authRepository = FakeAuthRepositoryImpl(shouldSucceed = false)
        val getIsLoggedInUseCase = GetIsLoggedInUseCase(authRepository = authRepository)
        val getTokenUseCase = GetTokenUseCase(authRepository = authRepository)

        authViewModelUnderTest = AuthViewModel(
            coroutineDispatcher = coroutineTestDispatcher,
            getIsLoggedInUseCase = getIsLoggedInUseCase,
            getTokensUseCase = getTokenUseCase,
        )

        authViewModelUnderTest.authenticate(code = "code")
        authViewModelUnderTest.authState.test {
            val result = awaitItem()
            if (result is UIState.Success) {
                assertThat(result.data).isEqualTo("Something went wrong")
            }
        }
    }
}
