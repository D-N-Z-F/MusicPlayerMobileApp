package com.example.musicplayermobileapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicplayermobileapplication.data.model.Song

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSong(song: Song)
    @Query("SELECT * FROM Song")
    fun getAllSongs()
    @Query("SELECT * FROM Song WHERE id = :id")
    fun getSongById(id: Int)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSong(song: Song)
    @Delete
    fun deleteSong(song: Song)
}