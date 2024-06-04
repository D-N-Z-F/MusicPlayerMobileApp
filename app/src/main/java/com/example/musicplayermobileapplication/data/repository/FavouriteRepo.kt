package com.example.musicplayermobileapplication.data.repository

import com.example.musicplayermobileapplication.data.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepo {
    fun addFavourites(favourite: Favourite)
    fun getAllUserFavourites(userId: Int): Flow<Favourite?>
    fun updateFavourites(favourite: Favourite)
}