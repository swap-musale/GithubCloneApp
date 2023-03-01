package com.example.githubcloneapp.di

import com.example.domain.model.GithubUseCases
import com.example.domain.useCase.GetUserAccessToken
import com.example.domain.useCase.GetUserInfoUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { GetUserInfoUseCase(githubRepository = get()) }
    single { GetUserAccessToken(githubRepository = get()) }

    single {
        GithubUseCases(
            getUserInfoUseCase = get(),
            getUserAccessToken = get()
        )
    }
}