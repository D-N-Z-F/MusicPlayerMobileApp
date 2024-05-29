package com.example.musicplayermobileapplication.data.repository.repoImpl

import com.example.musicplayermobileapplication.data.db.UserDao
import com.example.musicplayermobileapplication.data.model.User
import com.example.musicplayermobileapplication.data.repository.repo.UserRepo

class UserRepoImpl(private val dao: UserDao): UserRepo {
    override fun addUser(user: User) { dao.addUser(user) }
    override fun getUser(username: String, password: Int): User? = dao.getUser(username, password)
    override fun updateUser(user: User) { dao.updateUser(user) }
    override fun deleteUser(user: User) { dao.deleteUser(user) }
}