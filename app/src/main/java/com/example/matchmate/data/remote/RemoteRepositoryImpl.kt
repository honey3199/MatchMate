package com.example.matchmate.data.remote

import jakarta.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : RemoteRepository {
    override suspend fun getUsers() = apiService.getUsersFromRemote()
}