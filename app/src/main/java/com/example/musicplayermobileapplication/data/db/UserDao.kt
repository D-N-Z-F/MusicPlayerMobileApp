package com.example.musicplayermobileapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicplayermobileapplication.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun getUser(username: String, password: Int)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)
    @Delete
    fun deleteUser(user: User)
}