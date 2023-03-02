package com.example.domain.repositories

import com.example.domain.entities.User

interface UserRepository {
    suspend fun getUser(): User
}
