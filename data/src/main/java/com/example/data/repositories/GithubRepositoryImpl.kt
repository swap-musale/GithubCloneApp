package com.example.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.example.data.GetFavoriteRepositoryQuery
import com.example.data.GetUserNameQuery
import com.example.data.GetUserQuery
import com.example.domain.entities.FavoriteRepository
import com.example.domain.entities.Owner
import com.example.domain.entities.RepositoryNode
import com.example.domain.entities.User
import com.example.domain.repositories.GithubRepository

class GithubRepositoryImpl(private val apolloClient: ApolloClient) : GithubRepository {
    override suspend fun getUser(): User {
        return apolloClient.query(GetUserNameQuery())
            .execute().data?.viewer?.login?.let { username ->
            val response = apolloClient.query(GetUserQuery(username = username)).execute()
            if (response.hasErrors()) {
                throw Exception(response.errors?.joinToString())
            }
            response.data?.user?.let { nnUser ->
                User(
                    email = nnUser.email,
                    name = nnUser.name.orEmpty(),
                    avatarUrl = nnUser.avatarUrl.toString(),
                )
            }
        } ?: throw Exception("No user found!")
    }

    override suspend fun getFavoriteRepository(): FavoriteRepository {
        return apolloClient.query(GetFavoriteRepositoryQuery())
            .execute().data?.viewer?.repositories?.let { repositories ->
            repositories.nodes?.let {
                FavoriteRepository(
                    totalCount = repositories.totalCount,
                    nodes = repositories.nodes.map { nodeData ->
                        RepositoryNode(
                            name = nodeData?.name.orEmpty(),
                            nameWithOwner = nodeData?.nameWithOwner.orEmpty(),
                            url = nodeData?.url.toString(),
                            hasViewerStarred = nodeData?.viewerHasStarred,
                            owner = Owner(
                                avatarUrl = nodeData?.owner?.avatarUrl.toString(),
                                login = nodeData?.owner?.login.orEmpty(),
                            ),
                        )
                    },
                )
            }
        } ?: throw Exception("Error while fetching Favorite Repositories")
    }
}
