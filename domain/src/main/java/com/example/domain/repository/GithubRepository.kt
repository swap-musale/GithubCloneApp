package com.example.domain.repository

import com.example.domain.model.AccessToken

interface GithubRepository {
    suspend fun getAccessToken(): AccessToken
}