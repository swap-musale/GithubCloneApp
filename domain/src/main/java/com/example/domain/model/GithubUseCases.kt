package com.example.domain.model

import com.example.domain.useCase.GetUserAccessToken
import com.example.domain.useCase.GetUserInfoUseCase

data class GithubUseCases(
    val getUserInfoUseCase: GetUserInfoUseCase,
    val getUserAccessToken: GetUserAccessToken
)