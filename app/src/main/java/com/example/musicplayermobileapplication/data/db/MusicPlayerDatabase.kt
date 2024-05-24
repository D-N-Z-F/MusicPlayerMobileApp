package com.example.musicplayermobileapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musicplayermobileapplication.core.Converters
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.model.User

@Database(entities = [User::class, Song::class, Playlist::class], version = 3)
@TypeConverters(Converters::class)
abstract class MusicPlayerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object { const val NAME = "music_player_database" }
}