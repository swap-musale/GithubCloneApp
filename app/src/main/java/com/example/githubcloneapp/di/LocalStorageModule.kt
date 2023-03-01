package com.example.githubcloneapp.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.localStorage.DataStoreManagerImpl
import com.example.domain.localStorage.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val LocalStorageModule = module {

    single {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = {
                androidContext().preferencesDataStoreFile(name = "user_data")
            }
        )
    }

    single<DataStoreManager> { DataStoreManagerImpl(dataStore = get()) }
}