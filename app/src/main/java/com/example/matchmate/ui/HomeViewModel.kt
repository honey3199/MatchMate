package com.example.matchmate.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchmate.data.local.LocalRepository
import com.example.matchmate.data.local.User
import com.example.matchmate.data.models.Results
import com.example.matchmate.data.remote.RemoteRepository
import com.example.matchmate.data.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private val TAG = "HomeViewModel"

    private val _userData = MutableLiveData<RemoteResponse>()
    val userData: LiveData<RemoteResponse> = _userData
    val cachedUserData: LiveData<List<User>> = localRepository.getUsersLive()

    var currentUserAge: Int? = null
    var currentUserCity: String? = null

    var count = 50

    fun updateSelected(user: User) = viewModelScope.launch {
        localRepository.updateUser(user)
    }

    init {
        if (cachedUserData.value == null || cachedUserData.value!!.isEmpty()) {
            fetchUsersFromRemote()
        }
    }

    fun fetchUsersFromRemote() = viewModelScope.launch {
        _userData.value = RemoteResponse.Loading
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
                        picture = user.picture,
                        matchScore = computeMatchScore(user)
                    )
//                    }
                }
                // Store in Room for offline access
                localRepository.insertUsers(users)
                _userData.postValue(RemoteResponse.Success(users))
            }

            Resource.Status.ERROR -> {
                Log.d(TAG, "fetchDetailsFromRemote: Failure")
                result.message?.let {
                    Log.d(TAG, it)
                    _userData.postValue(RemoteResponse.Error(it))
                }
            }
        }
    }

    fun computeMatchScore(userA: Results): Int {
        var score = 0

        val ageDifference = kotlin.math.abs(userA.dob.age - (currentUserAge ?: 0))
        val ageScore = when {
            ageDifference <= 2 -> 70
            ageDifference <= 5 -> 50
            ageDifference <= 8 -> 30
            else -> 10
        }
        score += ageScore

        val cityScore = if (userA.location.city.equals(currentUserCity, ignoreCase = true)) 30 else 0
        score += cityScore

        return score.coerceIn(0, 100)
    }
}

sealed interface RemoteResponse {
    data object Loading : RemoteResponse
    data class Error(val errorMessage: String) : RemoteResponse
    data class Success(val users: List<User>) : RemoteResponse
}