package com.example.ghclone.viewmodel

import app.cash.turbine.test
import com.example.domain.entities.FavoriteRepository
import com.example.domain.entities.Owner
import com.example.domain.entities.RepositoryNode
import com.example.domain.repositories.GithubRepository
import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.ghclone.ui.home.HomeViewModel
import com.example.ghclone.utils.UIState
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MockHomeViewModel {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var githubRepository: GithubRepository

    private val coroutineTestDispatcher = StandardTestDispatcher()
    private lateinit var homeViewModelUnderTest: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)
        homeViewModelUnderTest = HomeViewModel(
            coroutineDispatcher = coroutineTestDispatcher,
            getFavoriteRepositoryUseCase = GetFavoriteRepositoryUseCase(githubRepository = githubRepository),
        )
    }

    @Test
    fun onHomeLaunch_CallFavApi_OnSuccessReturnsTrue() = runTest {
        val nodeList = listOf(
            RepositoryNode(
                nameWithOwner = "",
                name = "",
                url = "",
                owner = Owner(avatarUrl = "", login = ""),
            ),
        )

        val mockResponse = FavoriteRepository(nodes = nodeList, totalCount = nodeList.size)
        coEvery { githubRepository.getFavoriteRepository() } returns mockResponse
        homeViewModelUnderTest.getFavoriteRepositories()
        homeViewModelUnderTest.favoriteRepositoryState.test {
            val result = awaitItem()
            if (result is UIState.Success) {
                assertThat(result.data.nodes.isNotEmpty()).isFalse()
                awaitComplete()
            }
        }
    }
}
