package com.example.matchmate.data.local

interface LocalRepository {
    suspend fun insertUsers(users: List<User>)
    suspend fun getUsers(): List<User>
}