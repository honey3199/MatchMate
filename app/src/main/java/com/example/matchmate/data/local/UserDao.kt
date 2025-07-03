package com.example.matchmate.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("UPDATE user SET status = :status WHERE id = :id")
    suspend fun updateUser(status: Status, id: String)

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user")
    fun getUsersLive(): LiveData<List<User>>
}