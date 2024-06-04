package com.example.musicplayermobileapplication.data.repository

import com.example.musicplayermobileapplication.data.db.FavouriteDao
import com.example.musicplayermobileapplication.data.model.Favourite
import kotlinx.coroutines.flow.Flow

class FavouriteRepoImpl(private val dao: FavouriteDao): FavouriteRepo {
    override fun addFavourites(favourite: Favourite) = dao.addFavourites(favourite)
    override fun getAllUserFavourites(userId: Int): Flow<Favourite?> = dao.getAllUserFavourites(userId)
    override fun updateFavourites(favourite: Favourite) = dao.updateFavourites(favourite)
}