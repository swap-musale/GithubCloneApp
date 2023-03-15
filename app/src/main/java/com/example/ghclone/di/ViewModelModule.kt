package com.example.ghclone.di

import com.example.ghclone.ui.auth.AuthViewModel
import com.example.ghclone.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel {
        AuthViewModel(
            getTokensUseCase = get(),
            getIsLoggedInUseCase = get(),
        )
    }
    viewModel { HomeViewModel(getFavoriteRepositoryUseCase = get()) }
}
