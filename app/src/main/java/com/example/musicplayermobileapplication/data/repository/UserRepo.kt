package com.example.musicplayermobileapplication.data.repository

import com.example.musicplayermobileapplication.data.model.PublicUserDetails
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    fun addUser(user: User)
    fun validateUser(username: String, password: Int): User?
    fun getUserById(id: Int): Flow<PublicUserDetails?>
    fun updateUser(user: User)
    fun deleteUser(user: User)
}