package com.example.android_repository_pattern.data.repository

import com.example.android_repository_pattern.data.local.UserLocalDataSource
import com.example.android_repository_pattern.data.remote.UserRemoteDataSource
import com.example.android_repository_pattern.domain.entity.User
import com.example.android_repository_pattern.domain.repository.UserRepository

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        var users = localDataSource.getUsers()
        if (users.isEmpty()) {
            users = remoteDataSource.getUsers()
            localDataSource.addAllUsers(users)
        }
        return users
    }

    override suspend fun getUserById(id: Int): User? {
        val localUser = localDataSource.getUserById(id)
        if (localUser != null) {
            return localUser
        }

        val remoteUser = remoteDataSource.getUserById(id)
        if (remoteUser != null) {
            localDataSource.addUser(remoteUser)
            return remoteUser
        }

        return null
    }

    override suspend fun addUser(user: User) {
        val newUser = remoteDataSource.addUser(user)
        localDataSource.addUser(newUser)
    }

    override suspend fun updateUser(user: User) {
        remoteDataSource.updateUser(user)
        localDataSource.updateUser(user)
    }

    override suspend fun deleteUserById(id: Int) {
        remoteDataSource.deleteUserById(id)
        localDataSource.deleteUserById(id)
    }
}