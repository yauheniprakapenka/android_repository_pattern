package com.example.android_repository_pattern.data.remote

import com.example.android_repository_pattern.domain.entity.User

class UserRemoteDataSource {
    private val mockUsers = mutableListOf(
        User(1, "Remote Alice", "alice@example.com"),
        User(2, "Remote Bob", "bob@example.com"),
        User(3, "Remote Charlie", "charlie@example.com"),
        User(4, "Remote Diana", "diana@example.com"),
        User(5, "Remote Ethan", "ethan@example.com")
    )

    fun getUsers(): List<User> {
        Thread.sleep(1000)
        return mockUsers
    }

    fun getUserById(id: Int): User? {
        Thread.sleep(500)
        return mockUsers.find { it.id == id }
    }

    fun addUser(user: User): User {
        Thread.sleep(500)
        return user.copy(id = (mockUsers.maxByOrNull { it.id }?.id ?: 0) + 1)
    }

    fun updateUser(user: User) {
        Thread.sleep(500)
        val index = mockUsers.indexOfFirst { it.id == user.id }
        if (index != -1) {
            mockUsers[index] = user
        }
    }

    fun deleteUserById(id: Int) {
        Thread.sleep(500)
        val userToRemove = mockUsers.find { it.id == id }
        if (userToRemove != null) {
            mockUsers.remove(userToRemove)
        }
    }
}
