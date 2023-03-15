package com.example.ghclone.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.example.data.AuthorizationInterceptor
import com.example.data.Constants
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val NetworkModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v("HTTP Client", null, message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
    factory {
        ApolloClient.Builder()
            .serverUrl(Constants.BaseUrl)
            .addHttpInterceptor(AuthorizationInterceptor(dataStore = get()))
            .addHttpInterceptor(LoggingInterceptor(level = LoggingInterceptor.Level.BODY))
            .build()
    }
}
