package com.example.matchmate.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.matchmate.data.local.LocalRepository
import com.example.matchmate.data.local.Status
import com.example.matchmate.data.local.User
import com.example.matchmate.data.remote.RemoteRepository
import com.example.matchmate.data.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val TAG = "HomeViewModel"

    private val users = mutableListOf<User>()

    var count = 30

    fun updateSelected(index: Int, status: Status) {
        // update date to local database
    }

    fun fetchUsersFromRemote() = flow {
        emit(RemoteResponse.Loading)
        val result = remoteRepository.getUsers(count)
        when (result.status) {
            Resource.Status.SUCCESS -> {
                Log.d(TAG, "fetchDetailsFromRemote: Success")
                Log.d(TAG, result.data?.results.toString())
                val remoteUser = result.data?.results ?: emptyList()
                val users = remoteUser.map { user ->
//                    if (true) { // userMatchScore
                    User(
                        id = "${user.id.name}${user.id.value}",
                        email = user.email,
                        age = user.dob.age,
                        gender = user.gender,
                        location = user.location,
                        name = "${user.name.first} ${user.name.last}",
                        phone = user.phone,
                        picture = user.picture
                    )
//                    }
                }
                // Store in Room for offline access
                localRepository.insertUsers(users)
                emit(RemoteResponse.Success(users))
            }

            Resource.Status.ERROR -> {
                Log.d(TAG, "fetchDetailsFromRemote: Failure")
                result.message?.let {
                    Log.d(TAG, it)
                    emit(RemoteResponse.Error(it))
                }
            }
        }
    }

    private fun userMatchScore() {

    }
}

sealed interface RemoteResponse {
    data object Loading : RemoteResponse
    data class Error(val errorMessage: String) : RemoteResponse
    data class Success(val users: List<User>) : RemoteResponse
}