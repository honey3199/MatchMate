package com.example.matchmate.data.remote

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("api/?results=10")
    suspend fun getUsersFromRemote(): Response<JsonObject>
}