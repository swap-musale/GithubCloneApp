package com.example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthorizationInterceptor constructor(
    private val dataStore: DataStore<Preferences>
) : HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        var token = runBlocking {
            dataStore.data.map { it[Constants.AccessTokenKey] }.first()
        }

        val response =
            chain.proceed(
                request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )

        return if (response.statusCode == 401) {
            token = runBlocking {
                dataStore.data.map { it[Constants.AccessTokenKey] }.first()
            }
            chain.proceed(
                request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
        } else {
            response
        }
    }
}
