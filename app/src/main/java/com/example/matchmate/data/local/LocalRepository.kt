package com.example.matchmate.data.local

import com.example.matchmate.data.models.User

interface LocalRepository {
    suspend fun insertUsers(users: List<User>)
    suspend fun getUsers(): List<User>
}