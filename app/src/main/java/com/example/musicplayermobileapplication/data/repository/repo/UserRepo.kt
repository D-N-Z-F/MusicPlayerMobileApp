package com.example.musicplayermobileapplication.data.repository.repo

import com.example.musicplayermobileapplication.data.model.User

interface UserRepo {
    fun addUser(user: User)
    fun getUser(username: String, password: Int): User?
    fun updateUser(user: User)
    fun deleteUser(user: User)
}