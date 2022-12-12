package com.dongchyeon.passwordkeeper.presentation.di

import android.content.Context
import androidx.room.Room
import com.dongchyeon.passwordkeeper.data.datasource.AppDatabase
import com.dongchyeon.passwordkeeper.data.datasource.AuthDataSource
import com.dongchyeon.passwordkeeper.data.datasource.MemoDao
import com.dongchyeon.passwordkeeper.data.datasource.MemoDataSource
import com.dongchyeon.passwordkeeper.data.repository.AuthRepository
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context):
            AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesMemoDao(appDatabase: AppDatabase): MemoDao {
        return appDatabase.memosDao()
    }

    @Provides
    @Singleton
    fun providesMemoDataSource(memoDao: MemoDao): MemoDataSource {
        return MemoDataSource(memoDao)
    }

    @Provides
    @Singleton
    fun providesAuthDataSource(@ApplicationContext context: Context): AuthDataSource {
        return AuthDataSource(context)
    }

    @Provides
    @Singleton
    fun providesMemoRepository(memoDataSource: MemoDataSource): MemoRepository {
        return MemoRepository(memoDataSource)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepository(authDataSource)
    }
}