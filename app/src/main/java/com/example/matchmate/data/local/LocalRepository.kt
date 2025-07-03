package com.example.matchmate.data.local

import androidx.lifecycle.LiveData

interface LocalRepository {
    suspend fun insertUsers(users: List<User>)
    suspend fun getUsers(): List<User>
    suspend fun updateUser(user: User)
    fun getUsersLive(): LiveData<List<User>>
}