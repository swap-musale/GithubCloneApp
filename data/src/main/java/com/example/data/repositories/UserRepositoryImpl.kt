package com.example.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.example.data.GetUserNameQuery
import com.example.data.GetUserQuery
import com.example.domain.entities.User
import com.example.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val apolloClient: ApolloClient
) : UserRepository {
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
                    avatarUrl = nnUser.avatarUrl.toString()
                )
            }
        } ?: throw Exception("No user found!")
    }
}
