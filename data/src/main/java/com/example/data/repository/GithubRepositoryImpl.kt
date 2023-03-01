package com.example.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.data.GetUserInfoQuery
import com.example.data.source.remote.GithubService
import com.example.data.toUserInfo
import com.example.domain.model.AccessTokenRequest
import com.example.domain.model.AccessTokenResponse
import com.example.domain.model.Repository
import com.example.domain.model.UserInfo
import com.example.domain.repository.GithubRepository

class GithubRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val githubService: GithubService
) : GithubRepository {
    override suspend fun getUserInfo(): UserInfo {
        return apolloClient
            .query(GetUserInfoQuery())
            .execute()
            .data
            ?.viewer?.toUserInfo() ?: UserInfo(
            name = "",
            email = "",
            avatarUrl = "",
            location = "",
            url = "",
            company = "",
            websiteUrl = "",
            repositories = Repository(nodes = emptyList(), totalCount = 0)
        )
    }

    override suspend fun getUserAccessToken(request: AccessTokenRequest): AccessTokenResponse? =
        githubService.getUserAccessToken(request).body()
}