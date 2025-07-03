package com.example.matchmate.data.remote

import com.example.matchmate.data.models.UserResponse
import jakarta.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : BaseDataSource(), RemoteRepository {
    override suspend fun getUsers(count: Int): Resource<UserResponse> = getResult {
        apiService.getUsersFromRemote(count)
    }
}