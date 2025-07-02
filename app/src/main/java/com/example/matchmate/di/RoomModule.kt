package com.example.matchmate.di

import android.app.Application
import androidx.room.Room
import com.example.matchmate.data.local.MatchMateDatabase
import com.example.matchmate.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): MatchMateDatabase = synchronized(this) {
        Room.databaseBuilder(
            context = application.applicationContext,
            klass = MatchMateDatabase::class.java,
            name = "match-mate-database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration(false).build()
    }

    @Singleton
    @Provides
    fun provideDao(appDatabase: MatchMateDatabase): UserDao = appDatabase.userDao()
}