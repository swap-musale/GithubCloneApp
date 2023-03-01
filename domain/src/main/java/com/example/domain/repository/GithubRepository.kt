package com.example.domain.repository

import com.example.domain.model.AccessTokenRequest
import com.example.domain.model.AccessTokenResponse
import com.example.domain.model.UserInfo

interface GithubRepository {
    suspend fun getUserInfo(): UserInfo
    suspend fun getUserAccessToken(request: AccessTokenRequest): AccessTokenResponse?
}