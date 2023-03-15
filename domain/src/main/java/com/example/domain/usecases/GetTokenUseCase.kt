package com.example.domain.usecases

import com.example.domain.repositories.AuthRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.safeCall

class GetTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(code: String): Resource<String> = safeCall {
        authRepository.getTokens(code = code)
    }
}
