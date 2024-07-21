package com.example.android_repository_pattern.data.local

import com.example.android_repository_pattern.domain.entity.User

class UserLocalDataSource {
    private val users = mutableListOf<User>()

    fun getUsers(): List<User> = users.toList()

    fun getUserById(id: Int): User? = users.find { it.id == id }

    fun addUser(user: User) {
        users.add(user)
    }

    fun addAllUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
    }

    fun updateUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user
        }
    }

    fun deleteUserById(id: Int) {
        users.removeAll { it.id == id }
    }
}