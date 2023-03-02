package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.entities.User
import com.example.domain.repositories.UserRepository
import com.example.domain.safeCall

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Resource<User> = safeCall { userRepository.getUser() }
}
