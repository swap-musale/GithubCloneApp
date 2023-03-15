package com.example.domain.usecases

import com.example.domain.entities.User
import com.example.domain.repositories.GithubRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.safeCall

class GetUserUseCase(private val githubRepository: GithubRepository) {
    suspend operator fun invoke(): Resource<User> = safeCall { githubRepository.getUser() }
}
