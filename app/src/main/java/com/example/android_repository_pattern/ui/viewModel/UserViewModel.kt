package com.example.android_repository_pattern.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repository_pattern.domain.entity.User
import com.example.android_repository_pattern.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _loading.value = true
            _users.value = withContext(Dispatchers.IO) { repository.getUsers() } ?: emptyList()
            _loading.value = false
        }
    }

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                repository.addUser(User(0, name, email))
            }
            loadUsers()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                repository.updateUser(user)
            }
            loadUsers()
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                repository.deleteUserById(id)
            }
            loadUsers()
        }
    }
}