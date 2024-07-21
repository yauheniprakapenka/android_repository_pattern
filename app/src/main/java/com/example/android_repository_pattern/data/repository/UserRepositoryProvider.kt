package com.example.android_repository_pattern.data.repository

import com.example.android_repository_pattern.data.local.UserLocalDataSource
import com.example.android_repository_pattern.data.remote.UserRemoteDataSource
import com.example.android_repository_pattern.domain.repository.UserRepository

object UserRepositoryProvider {
    private val localDataSource: UserLocalDataSource by lazy { UserLocalDataSource() }
    private val remoteDataSource: UserRemoteDataSource by lazy { UserRemoteDataSource() }
    private val repository: UserRepository by lazy {
        UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    fun provideRepository(): UserRepository = repository
}