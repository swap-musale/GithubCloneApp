package com.example.ghclone.usecase

import com.example.domain.repositories.GithubRepository
import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.domain.utils.Resource
import com.example.ghclone.repository.FakeGithubRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFavoriteRepositoryUseCaseTest {

    private lateinit var githubRepository: GithubRepository
    lateinit var getFavoriteRepositoryUseCase: GetFavoriteRepositoryUseCase

    @Test
    fun onHomeScreenLaunch_ApiGivesData_ReturnsTrue() = runBlocking {
        githubRepository = FakeGithubRepositoryImpl()
        getFavoriteRepositoryUseCase = GetFavoriteRepositoryUseCase(githubRepository = githubRepository)
        val result = getFavoriteRepositoryUseCase()
        if (result is Resource.Success) {
            assertThat(result.data.nodes.isNotEmpty()).isTrue()
        }
    }
}
