package com.example.ghclone.repository

import com.example.domain.entities.FavoriteRepository
import com.example.domain.entities.Owner
import com.example.domain.entities.RepositoryNode
import com.example.domain.entities.User
import com.example.domain.repositories.GithubRepository

class FakeGithubRepositoryImpl : GithubRepository {

    override suspend fun getUser(): User {
        return User(email = "test@gmail.com", name = "Test", avatarUrl = "www.google.com")
    }

    override suspend fun getFavoriteRepository(): FavoriteRepository {
        val nodeList = listOf(
            RepositoryNode(
                nameWithOwner = "",
                name = "",
                url = "",
                owner = Owner(avatarUrl = "", login = ""),
            ),
        )

        return FavoriteRepository(
            nodes = nodeList,
            totalCount = nodeList.size,
        )
    }
}
