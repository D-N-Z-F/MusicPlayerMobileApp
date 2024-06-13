package com.example.musicplayermobileapplication.core.services

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.musicplayermobileapplication.core.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// This class acts as a service class that provides authentication methods throughout the app.
class AuthService @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun login(id: Int, username: String) {
        sharedPreferences.edit {
            putBoolean(Constants.IS_LOGGED_IN, true)
            putString(Constants.USERNAME, username)
            putInt(Constants.USER_ID, id)
            apply()
        }
    }
    fun logout() {
        sharedPreferences.edit {
            remove(Constants.IS_LOGGED_IN)
            remove(Constants.USERNAME)
            remove(Constants.USER_ID)
            apply()
        }
    }
    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false)
    fun getUserId(): Int = sharedPreferences.getInt(Constants.USER_ID, 0)
}