package com.dongchyeon.passwordkeeper.di

import android.content.Context
import com.dongchyeon.passwordkeeper.data.pref.SharedPrefStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context) : SharedPrefStorage {
        return SharedPrefStorage(context = context)
    }
}