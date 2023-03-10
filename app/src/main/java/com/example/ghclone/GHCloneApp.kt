package com.example.ghclone

import android.app.Application
import com.example.ghclone.di.DataStoreModule
import com.example.ghclone.di.NetworkModule
import com.example.ghclone.di.RepositoryModule
import com.example.ghclone.di.UseCaseModule
import com.example.ghclone.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GHCloneApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GHCloneApp)
            androidLogger(Level.DEBUG)
            modules(
                DataStoreModule,
                NetworkModule,
                RepositoryModule,
                UseCaseModule,
                ViewModelModule,
            )
        }
    }
}
