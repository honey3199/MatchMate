package com.example.matchmate.di

import com.example.matchmate.data.local.LocalRepository
import com.example.matchmate.data.local.LocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLocalRepository(
        impl: LocalRepositoryImpl
    ): LocalRepository
}