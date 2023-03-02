package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.repositories.AuthRepository
import com.example.domain.safeCall

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Resource<Unit> = safeCall { authRepository.logout() }
}
