package com.example.ghclone

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GHCApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GHCApp)
            androidLogger(Level.DEBUG)
            modules(
                DataSourceModule,
                NetworkModule,
                RepositoryModule,
                UseCaseModule,
                ViewModelModule
            )
        }
    }
}
