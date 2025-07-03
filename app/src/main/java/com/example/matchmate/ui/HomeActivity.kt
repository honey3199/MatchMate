package com.example.matchmate.ui

import android.os.Bundle
import android.view.View
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

        binding.apply {
            recyclerView.apply {
                adapter = userAdapter
                layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
            }

            btnSubmit.setOnClickListener {
                if (!etAge.text.isEmpty() && !etCity.text.isEmpty()) {
                    etCity.visibility = View.GONE
                    etAge.visibility = View.GONE
                    btnSubmit.visibility = View.GONE

                    viewModel.currentUserAge = etAge.text.toString().toInt()
                    viewModel.currentUserCity = etCity.text.toString()

                    viewModel.fetchUsersFromRemote()
                    recyclerView.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@HomeActivity, "Add input to proceed..!!", LENGTH_LONG).show()
                }
            }
        }


        with(viewModel) {
            cachedUserData.observe(this@HomeActivity) {
                binding.apply {
                    if (it.isEmpty()) {
                        etCity.visibility = View.VISIBLE
                        etAge.visibility = View.VISIBLE
                        btnSubmit.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        etCity.visibility = View.GONE
                        etAge.visibility = View.GONE
                        btnSubmit.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        userAdapter.setUserData(it)
                    }
                }
            }
            userData.observe(this@HomeActivity) {
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
}