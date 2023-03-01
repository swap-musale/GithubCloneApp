package com.example.domain.model

data class UserInfo(
    val name: String,
    val email: String,
    val avatarUrl: String,
    val location: String,
    val url: String,
    val company: String,
    val websiteUrl: String,
    val repositories: Repository
)

data class Repository(
    val nodes: List<Node>,
    val totalCount: Int
)

data class Node(
    val createdAt: String,
    val description: String,
    val name: String,
    val isPrivate: Boolean,
    val url: String,
    val primaryLanguage: PrimaryLanguage
)

data class PrimaryLanguage(
    val name: String,
    val color: String
)