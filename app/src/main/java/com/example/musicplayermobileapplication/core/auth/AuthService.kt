package com.example.musicplayermobileapplication.core.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.musicplayermobileapplication.core.constants.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) {
    fun login(username: String) {
        sharedPreferences.edit {
            putBoolean(Constants.IS_LOGGED_IN, true)
            putString(Constants.USERNAME, username)
            apply()
        }
    }
    fun logout() {
        sharedPreferences.edit {
            remove(Constants.IS_LOGGED_IN)
            remove(Constants.USERNAME)
            apply()
        }
    }
    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false)
}