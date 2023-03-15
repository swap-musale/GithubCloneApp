package com.example.ghclone.usecase

import com.example.domain.repositories.GithubRepository
import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.domain.utils.Resource
import com.example.ghclone.repository.FakeGithubRepositoryImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavoriteRepositoryUseCaseTest {

    // Using Fake's
    private lateinit var fakeGithubRepository: GithubRepository
    lateinit var getFavoriteRepositoryUseCase: GetFavoriteRepositoryUseCase

    // Using Mockk
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var githubRepository: GithubRepository
    private lateinit var favoriteRepositoryUseCaseUnderTest: GetFavoriteRepositoryUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favoriteRepositoryUseCaseUnderTest =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
    }

    @Test
    fun apiGivesData_onHomeScreenLaunch_ReturnsTrue() = runTest {
        fakeGithubRepository = FakeGithubRepositoryImpl()
        getFavoriteRepositoryUseCase =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        val result = getFavoriteRepositoryUseCase()
        if (result is Resource.Success) {
            assertThat(result.data.nodes.isNotEmpty()).isTrue()
        }
    }

    @Test
    fun apiGivesData_onHomeScreenLaunch_ReturnTrue() = runTest {
        favoriteRepositoryUseCaseUnderTest =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        val result = favoriteRepositoryUseCaseUnderTest()
        if (result is Resource.Success) {
            assertThat(result.data.nodes.isNotEmpty()).isTrue()
        }
    }

    @Test
    fun apiGivesError_onHomeScreenLaunch_ReturnsFalse() = runTest {
        favoriteRepositoryUseCaseUnderTest =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        val result = favoriteRepositoryUseCaseUnderTest()
        assertThat(result is Resource.Failure).isTrue()
    }
}
