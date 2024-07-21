package com.example.android_repository_pattern.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repository_pattern.R
import com.example.android_repository_pattern.data.repository.UserRepositoryProvider
import com.example.android_repository_pattern.domain.entity.User
import com.example.android_repository_pattern.ui.adapter.UserAdapter
import com.example.android_repository_pattern.ui.viewModel.UserViewModel
import com.example.android_repository_pattern.ui.viewModel.UserViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupRecyclerView()
        observeUsers()
        setupAddUserButton()
    }

    private fun setupViewModel() {
        val factory = UserViewModelFactory(UserRepositoryProvider.provideRepository())
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun setupRecyclerView() {
        val rvUsers = findViewById<RecyclerView>(R.id.rvUsers)
        rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(
            emptyList(),
            onDeleteClick = { userId -> viewModel.deleteUser(userId) },
            onItemClick = { user -> showUserDetailsDialog(user) }
        )
        rvUsers.adapter = userAdapter
    }

    private fun observeUsers() {
        viewModel.users.observe(this) { users ->
            userAdapter.updateUsers(users)
        }
        viewModel.loading.observe(this) { isLoading ->
            findViewById<ProgressBar>(R.id.progressBar).visibility =
                if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    private fun setupAddUserButton() {
        findViewById<Button>(R.id.btnAddUser).setOnClickListener {
            showUserDialog()
        }
    }

    private fun showUserDetailsDialog(user: User) {
        showUserDialog(user)
    }

    private fun showUserDialog(user: User? = null) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_user, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etEmail = dialogView.findViewById<EditText>(R.id.etEmail)

        user?.let {
            etName.setText(it.name)
            etEmail.setText(it.email)
        }

        val dialogTitle = if (user == null) "Add New User" else "User Details"
        val positiveButtonText = if (user == null) "Add" else "Update"

        AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setView(dialogView)
            .setPositiveButton(positiveButtonText) { _, _ ->
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                if (name.isNotBlank() && email.isNotBlank()) {
                    if (user == null) {
                        viewModel.addUser(name, email)
                    } else {
                        val updatedUser = User(user.id, name, email)
                        viewModel.updateUser(updatedUser)
                    }
                } else {
                    Toast.makeText(this, "Name and email cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}