package com.example.ghclone.usecase

import com.example.domain.repositories.GithubRepository
import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.domain.utils.Resource
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetFavoriteRepositoryMockTest {

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
    fun onHomeScreenLaunch_ApiGivesData_ReturnsTrue() = runBlocking {
        favoriteRepositoryUseCaseUnderTest =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        val result = favoriteRepositoryUseCaseUnderTest()
        if (result is Resource.Success) {
            Truth.assertThat(result.data.nodes.isNotEmpty()).isTrue()
        }
    }

    @Test
    fun onHomeScreenLaunch_ApiGivesError_ReturnsFalse() = runBlocking {
        favoriteRepositoryUseCaseUnderTest =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository) // todo error case
        val result = favoriteRepositoryUseCaseUnderTest()
        Truth.assertThat(result !is Resource.Success).isFalse()
    }
}
