package com.example.domain.repositories

import com.example.domain.entities.FavoriteRepository
import com.example.domain.entities.User

interface GithubRepository {
    suspend fun getUser(): User
    suspend fun getFavoriteRepository(): FavoriteRepository
}
