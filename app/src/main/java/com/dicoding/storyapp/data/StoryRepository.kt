package com.dicoding.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.dicoding.storyapp.api.ApiService
import com.dicoding.storyapp.database.StoryDatabase
import com.dicoding.storyapp.model.ListStoryItem
import com.dicoding.storyapp.preference.LoginPreference

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val pref: LoginPreference) {
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, pref),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}