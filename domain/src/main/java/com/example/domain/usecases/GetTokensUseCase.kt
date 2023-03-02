package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.repositories.AuthRepository
import com.example.domain.safeCall

class GetTokensUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(code: String): Resource<Unit> = safeCall {
        authRepository.getTokens(code = code)
    }
}
