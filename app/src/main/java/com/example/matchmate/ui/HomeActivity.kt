package com.example.matchmate.ui

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchmate.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private val userAdapter: UserDataAdapter = UserDataAdapter { user ->
        viewModel.updateSelected(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.fetchUsersFromRemote()

        viewModel.userData.observe(this) {
            when (it) {
                is RemoteResponse.Error -> {
                    Toast.makeText(this@HomeActivity, it.errorMessage, LENGTH_LONG).show()
                }

                RemoteResponse.Loading -> {
                    // Show Loader
                }

                is RemoteResponse.Success -> {
                    userAdapter.setUserData(users = it.users)
                }
            }
        }
    }
}