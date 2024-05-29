package com.example.musicplayermobileapplication.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.musicplayermobileapplication.core.utils.Constants
import com.example.musicplayermobileapplication.data.db.MusicPlayerDatabase
import com.example.musicplayermobileapplication.data.repository.UserRepo
import com.example.musicplayermobileapplication.data.repository.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)
    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): MusicPlayerDatabase =
        Room.databaseBuilder(context, MusicPlayerDatabase::class.java, MusicPlayerDatabase.NAME)
            .fallbackToDestructiveMigration().build()
    @Provides
    @Singleton
    fun provideUserRepo(db: MusicPlayerDatabase): UserRepo = UserRepoImpl(db.userDao())
}