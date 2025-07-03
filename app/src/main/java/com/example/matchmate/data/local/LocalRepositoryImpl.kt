package com.example.matchmate.data.local

import jakarta.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : LocalRepository {
    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users)
    }

    override suspend fun getUsers(): List<User> = userDao.getUsers()
}