package com.example.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.domain.model.AccessToken
import com.example.domain.repository.GithubRepository

class GithubRepositoryImpl(private val apolloClient: ApolloClient) : GithubRepository {
    override suspend fun getAccessToken(): AccessToken {
        return AccessToken("")
    }
}