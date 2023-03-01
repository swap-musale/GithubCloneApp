package com.example.githubcloneapp.di

import com.example.githubcloneapp.ui.auth.AuthenticationVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel {
        AuthenticationVM(
            githubUseCases = get(),
            dataStoreManager = get()
        )
    }
}