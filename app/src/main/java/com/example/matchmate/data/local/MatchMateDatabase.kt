package com.example.matchmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.matchmate.data.models.User

@Database(entities = [User::class], version = 1)
abstract class MatchMateDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}