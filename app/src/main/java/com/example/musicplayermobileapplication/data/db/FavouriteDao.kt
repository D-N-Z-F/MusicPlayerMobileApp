package com.example.musicplayermobileapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicplayermobileapplication.data.model.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavourites(favourite: Favourite)
    @Query("SELECT * FROM Favourite WHERE userId = :userId")
    fun getAllUserFavourites(userId: Int): Flow<Favourite?>
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavourites(favourite: Favourite)
}