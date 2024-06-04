package com.example.musicplayermobileapplication.data.repository

import com.example.musicplayermobileapplication.data.db.UserDao
import com.example.musicplayermobileapplication.data.model.User

class UserRepoImpl(private val dao: UserDao): UserRepo {
    override fun addUser(user: User) { dao.addUser(user) }
    override fun validateUser(
        username: String, password: Int
    ): User? = dao.validateUser(username, password)
    override fun updateUser(user: User) { dao.updateUser(user) }
    override fun deleteUser(user: User) { dao.deleteUser(user) }
}