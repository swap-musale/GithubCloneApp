package com.example.githubcloneapp.di

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.data.BuildConfig
import com.example.data.source.remote.GithubService
import com.example.domain.localStorage.DataStoreManager
import com.example.githubcloneapp.util.Constant
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {
    factory { provideOkHttpClient(dataStoreManager = get()) }
    factory { provideApolloClient(okHttpClient = get()) }
    factory { provideRetrofit(okHttpClient = get()) }
    factory { provideGithubService(retrofit = get()) }
}

fun provideOkHttpClient(dataStoreManager: DataStoreManager): OkHttpClient {

    val accessToken = getUserAccessToken(dataStoreManager = dataStoreManager)
    Log.d("TAG", "provideOkHttpClient 1: $accessToken")
    return OkHttpClient
        .Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val builder = original
                .newBuilder()
                .method(method = original.method, body = original.body)
            if (accessToken.isNotEmpty()) {
                Log.d("TAG", "provideOkHttpClient 2: $accessToken")
                builder.header(name = "Authorization", value = "bearer $accessToken")
            }
            chain.proceed(builder.build())
        }
        .also { builder ->
            if (BuildConfig.DEBUG) builder.addInterceptor(provideLoggingInterceptor())
        }
        .build()
}

private fun getUserAccessToken(dataStoreManager: DataStoreManager): String {
    return runBlocking {
        dataStoreManager.getUserAccessToken().firstOrNull().orEmpty()
    }
}

fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient
        .Builder()
        .serverUrl(Constant.GRAPH_QL_BASE_URL)
        .okHttpClient(okHttpClient)
        .build()
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constant.REST_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideGithubService(retrofit: Retrofit): GithubService =
    retrofit.create(GithubService::class.java)