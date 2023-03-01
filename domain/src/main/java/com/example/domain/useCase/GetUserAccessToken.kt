package com.example.domain.useCase

import com.example.domain.model.AccessTokenRequest
import com.example.domain.repository.GithubRepository

class GetUserAccessToken(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(request: AccessTokenRequest) =
        githubRepository.getUserAccessToken(request)
}