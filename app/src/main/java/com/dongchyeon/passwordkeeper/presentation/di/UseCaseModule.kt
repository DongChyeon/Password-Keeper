package com.dongchyeon.passwordkeeper.presentation.di

import com.dongchyeon.passwordkeeper.data.repository.AuthRepository
import com.dongchyeon.passwordkeeper.data.repository.MemoRepository
import com.dongchyeon.passwordkeeper.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    @Singleton
    fun providesGetMemosUseCase(memoRepository: MemoRepository): GetMemosUseCase {
        return GetMemosUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesGetMemosByCategoryUseCase(memoRepository: MemoRepository): GetMemosByCategoryUseCase {
        return GetMemosByCategoryUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesGetCategoriesUseCase(memoRepository: MemoRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesGetMemoByIdUseCase(memoRepository: MemoRepository): GetMemoByIdUseCase {
        return GetMemoByIdUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesInsertMemoUseCase(memoRepository: MemoRepository): InsertMemoUseCase {
        return InsertMemoUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateMemoUseCase(memoRepository: MemoRepository): UpdateMemoUseCase {
        return UpdateMemoUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteMemoUseCase(memoRepository: MemoRepository): DeleteMemoUseCase {
        return DeleteMemoUseCase(memoRepository)
    }

    @Provides
    @Singleton
    fun providesLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun providesSetPasswordUseCase(authRepository: AuthRepository): SetPasswordUseCase {
        return SetPasswordUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun providesGetIsRegisteredUseCase(authRepository: AuthRepository): GetIsRegisteredUseCase {
        return GetIsRegisteredUseCase(authRepository)
    }
}