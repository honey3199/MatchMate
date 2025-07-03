package com.example.matchmate.data.local

import androidx.lifecycle.LiveData
import jakarta.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : LocalRepository {
    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users)
    }

    override suspend fun getUsers(): List<User> = userDao.getUsers()

    override suspend fun updateUser(user: User) = userDao.updateUser(user.status, user.id)

    override fun getUsersLive(): LiveData<List<User>> = userDao.getUsersLive()
}