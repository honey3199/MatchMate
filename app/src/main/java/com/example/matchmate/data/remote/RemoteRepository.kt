package com.example.matchmate.data.remote

import com.google.gson.JsonObject
import retrofit2.Response

interface RemoteRepository {
    suspend fun getUsers(): Response<JsonObject>
}