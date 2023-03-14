package com.example.ghclone.viewmodel

import app.cash.turbine.test
import com.example.domain.entities.FavoriteRepository
import com.example.domain.repositories.GithubRepository
import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.ghclone.repository.FakeGithubRepositoryImpl
import com.example.ghclone.ui.home.HomeViewModel
import com.example.ghclone.utils.UIState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeHomeViewModel {

    private lateinit var githubRepository: GithubRepository
    private lateinit var getFavoriteRepositoryUseCase: GetFavoriteRepositoryUseCase
    private lateinit var homeViewModelUnderTest: HomeViewModel
    private val coroutineTestDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
    }

    @Test
    fun onHomeLaunch_CallFavApi_OnSuccessReturnsTrue() = runBlocking {
        githubRepository = FakeGithubRepositoryImpl()
        getFavoriteRepositoryUseCase =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        homeViewModelUnderTest =
            HomeViewModel(getFavoriteRepositoryUseCase = getFavoriteRepositoryUseCase)

        homeViewModelUnderTest.favoriteRepositoryState.test {
            val result = awaitItem()
            if (result is UIState.Success) {
                assertThat(result.data.nodes.isNotEmpty()).isTrue()
            }
        }
    }

    @Test
    fun onHomeLaunch_OnFavRepoApiSuccess_OnSuccessReturnsFalse() = runBlocking {
        githubRepository = FakeGithubRepositoryImpl()
        getFavoriteRepositoryUseCase =
            GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        homeViewModelUnderTest =
            HomeViewModel(getFavoriteRepositoryUseCase = getFavoriteRepositoryUseCase)

        homeViewModelUnderTest.favoriteRepositoryState.test {
            val result = awaitItem()
            var response: FavoriteRepository? = null
            if (result is UIState.Success) {
                response = result.data
            }

            assertThat(response?.nodes == null).isTrue()
        }
    }
}
