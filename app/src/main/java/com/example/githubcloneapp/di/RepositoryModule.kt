package com.example.githubcloneapp.di

import com.example.data.repository.GithubRepositoryImpl
import com.example.domain.repository.GithubRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<GithubRepository> {
        GithubRepositoryImpl(
            apolloClient = get(),
            githubService = get()
        )
    }
}