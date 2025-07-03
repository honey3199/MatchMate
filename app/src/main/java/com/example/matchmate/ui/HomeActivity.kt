package com.example.matchmate.ui

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchmate.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private val userAdapter: UserDataAdapter = UserDataAdapter { index, status ->
        viewModel.updateSelected(index, status)
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

        lifecycleScope.launch {
            viewModel.fetchUsersFromRemote().collect {
                when (it) {
                    is RemoteResponse.Error -> {
                        Toast.makeText(this@HomeActivity, it.errorMessage, LENGTH_LONG)
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
}