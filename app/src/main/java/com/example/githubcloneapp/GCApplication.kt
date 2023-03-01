package com.example.githubcloneapp

import android.app.Application
import com.example.githubcloneapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GCApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(androidContext = this@GCApplication)
            androidLogger(level = Level.INFO)
            modules(
                NetworkModule,
                RepositoryModule,
                UseCaseModule,
                ViewModelModule,
                LocalStorageModule
            )
        }
    }
}