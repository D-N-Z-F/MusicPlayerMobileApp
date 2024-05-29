package com.example.musicplayermobileapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.musicplayermobileapplication.core.converters.Converters
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.model.User

@Database(entities = [User::class, Song::class, Playlist::class], version = 2)
@TypeConverters(Converters::class)
abstract class MusicPlayerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    companion object {
        const val NAME = "music_player_database"
        val MIGRATION_1_2 = object: Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE User ADD COLUMN imagePath TEXT NOT NULL DEFAULT ''")
                db.execSQL("UPDATE Song SET imagePath = '' WHERE imagePath IS NULL")
            }
        }
    }
}