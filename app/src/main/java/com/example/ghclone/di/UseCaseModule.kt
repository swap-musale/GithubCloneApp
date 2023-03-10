package com.example.ghclone.di

import com.example.domain.usecases.GetFavoriteRepositoryUseCase
import com.example.domain.usecases.GetIsLoggedInUseCase
import com.example.domain.usecases.GetTokenUseCase
import com.example.domain.usecases.GetUserUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { GetTokenUseCase(authRepository = get()) }
    single { GetIsLoggedInUseCase(authRepository = get()) }
    single { GetUserUseCase(githubRepository = get()) }
    single { GetFavoriteRepositoryUseCase(githubRepository = get()) }
}