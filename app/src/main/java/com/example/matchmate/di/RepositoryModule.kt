package com.example.matchmate.di

import com.example.matchmate.data.local.LocalRepository
import com.example.matchmate.data.local.LocalRepositoryImpl
import com.example.matchmate.data.remote.RemoteRepository
import com.example.matchmate.data.remote.RemoteRepositoryImpl
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

    @Binds
    abstract fun bindRemoteRepository(
        impl: RemoteRepositoryImpl
    ): RemoteRepository
}