package com.dicoding.storyapp.utils

import android.content.Context
import com.dicoding.storyapp.api.ApiConfig
import com.dicoding.storyapp.database.StoryDatabase
import com.dicoding.storyapp.data.StoryRepository
import com.dicoding.storyapp.preference.LoginPreference

object Injection {
    fun provideRepository(pref: LoginPreference, context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService, pref)
    }
}