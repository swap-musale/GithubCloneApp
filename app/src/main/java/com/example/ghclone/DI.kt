package com.example.ghclone

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.example.data.AuthorizationInterceptor
import com.example.data.Constants
import com.example.data.dataStore
import com.example.data.repositories.AuthRepositoryImpl
import com.example.data.repositories.UserRepositoryImpl
import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.UserRepository
import com.example.domain.usecases.GetIsLoggedInUseCase
import com.example.domain.usecases.GetTokensUseCase
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.LogoutUseCase
import com.example.ghclone.viewmodels.MainViewModel
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DataSourceModule = module {
    single { get<Context>().dataStore }
}
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
val RepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(dataStore = get(), httpClient = get()) }
    factory<UserRepository> { UserRepositoryImpl(apolloClient = get()) }
}
val UseCaseModule = module {
    single { GetTokensUseCase(authRepository = get()) }
    single { GetIsLoggedInUseCase(authRepository = get()) }
    single { GetUserUseCase(userRepository = get()) }
    single { LogoutUseCase(authRepository = get()) }
}
val ViewModelModule = module {
    viewModel {
        MainViewModel(
            getTokensUseCase = get(),
            getIsLoggedInUseCase = get(),
            getUserUseCase = get()
        )
    }
}
