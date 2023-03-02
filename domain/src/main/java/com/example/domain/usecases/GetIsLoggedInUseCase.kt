package com.example.domain.usecases

import com.example.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetIsLoggedInUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<Boolean> = authRepository.isLoggedIn()
}
