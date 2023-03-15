package com.example.ghclone.di

import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.repositories.GithubRepositoryImpl
import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.GithubRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(dataStore = get(), httpClient = get()) }
    factory<GithubRepository> { GithubRepositoryImpl(apolloClient = get()) }
}
