package com.example.musicplayermobileapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicplayermobileapplication.data.model.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlaylist(playlist: Playlist)
    @Query("SELECT * FROM Playlist WHERE userId = :userId")
    fun getAllUserPlaylists(userId: Int): Flow<List<Playlist>>
    @Query("SELECT * FROM Playlist WHERE id = :id")
    fun getPlaylistById(id: Int): Flow<Playlist?>
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlaylist(playlist: Playlist)
    @Delete
    fun deletePlaylist(playlist: Playlist)
}