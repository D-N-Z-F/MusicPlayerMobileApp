package com.example.musicplayermobileapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicplayermobileapplication.data.model.Favourite
import com.example.musicplayermobileapplication.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun validateUser(username: String, password: Int): User?
    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): User?
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)
    @Delete
    fun deleteUser(user: User)
}