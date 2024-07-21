package com.example.android_repository_pattern.domain.repository

import com.example.android_repository_pattern.domain.entity.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUserById(id: Int)
}