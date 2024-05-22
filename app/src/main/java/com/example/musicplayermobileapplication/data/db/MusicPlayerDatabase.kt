package com.example.musicplayermobileapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicplayermobileapplication.data.model.User

@Database(entities = [User::class], version = 1)
abstract class MusicPlayerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object { const val NAME = "music_player_database" }
}