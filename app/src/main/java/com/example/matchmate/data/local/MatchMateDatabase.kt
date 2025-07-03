package com.example.matchmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class MatchMateDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}