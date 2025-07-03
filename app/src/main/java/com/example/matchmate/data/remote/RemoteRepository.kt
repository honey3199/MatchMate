package com.example.matchmate.data.remote

import com.example.matchmate.data.models.UserResponse

interface RemoteRepository {
    suspend fun getUsers(count: Int): Resource<UserResponse>
}