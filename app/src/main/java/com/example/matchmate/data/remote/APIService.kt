package com.example.matchmate.data.remote

import com.example.matchmate.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("api/")
    suspend fun getUsersFromRemote(
        @Query("results") count: Int
    ): Response<UserResponse>
}