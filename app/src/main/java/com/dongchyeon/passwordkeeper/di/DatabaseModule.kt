package com.dongchyeon.passwordkeeper.di

import android.content.Context
import androidx.room.Room
import com.dongchyeon.passwordkeeper.data.room.AppDatabase
import com.dongchyeon.passwordkeeper.data.room.MemosDao
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
    fun provideAppDatabase(@ApplicationContext context: Context) :
            AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-db"
            ).fallbackToDestructiveMigration()
                .build()
        }

    @Provides
    fun provideMemoDao(appDatabase: AppDatabase): MemosDao {
        return appDatabase.memosDao()
    }
}