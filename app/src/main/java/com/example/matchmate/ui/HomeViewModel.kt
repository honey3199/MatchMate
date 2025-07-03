package com.example.matchmate.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.data.local.LocalRepository
import com.example.matchmate.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val TAG = "HomeViewModel"

    var remoteResponse by mutableStateOf<RemoteResponse>(RemoteResponse.Loading)
        private set

    fun fetchUsersFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = remoteRepository.getUsers()
            if (response.isSuccessful) {
                Log.d(TAG, "fetchDetailsFromRemote: Success")
                Log.d(TAG, response.body().toString())
                remoteResponse = RemoteResponse.Success(response.body().toString())
            } else {
                Log.d(TAG, "fetchDetailsFromRemote: Failure")
                Log.d(TAG, response.errorBody().toString())
                remoteResponse = RemoteResponse.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.d(TAG, "fetchDetailsFromRemote: Exception")
            Log.d(TAG, e.printStackTrace().toString())
            remoteResponse = RemoteResponse.Error(e.printStackTrace().toString())
        }
    }
}

sealed interface RemoteResponse {
    data object Loading : RemoteResponse
    data class Error(val errorMessage: String) : RemoteResponse
    data class Success(val responseValue: String) : RemoteResponse
}