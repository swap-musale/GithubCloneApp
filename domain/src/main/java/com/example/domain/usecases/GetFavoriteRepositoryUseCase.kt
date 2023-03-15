package com.example.domain.usecases

import com.example.domain.entities.FavoriteRepository
import com.example.domain.repositories.GithubRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.safeCall

class GetFavoriteRepositoryUseCase(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(): Resource<FavoriteRepository> = safeCall {
        githubRepository.getFavoriteRepository()
    }
}
