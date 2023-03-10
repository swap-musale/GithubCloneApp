package com.example.domain.entities

data class FavoriteRepository(
    val nodes: List<RepositoryNode>,
    val totalCount: Int
)

data class RepositoryNode(
    val nameWithOwner: String,
    val name: String,
    val url: String,
    val owner: Owner,
    val hasViewerStarred: Boolean? = false
)

data class Owner(
    val avatarUrl: String,
    val login: String
)