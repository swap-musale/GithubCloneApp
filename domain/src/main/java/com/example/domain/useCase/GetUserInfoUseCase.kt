package com.example.domain.useCase

import com.example.domain.repository.GithubRepository

class GetUserInfoUseCase(private val githubRepository: GithubRepository) {
    suspend operator fun invoke() = githubRepository.getUserInfo()
}