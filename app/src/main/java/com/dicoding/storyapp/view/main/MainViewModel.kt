package com.dicoding.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.dicoding.storyapp.data.StoryRepository
import com.dicoding.storyapp.model.ListStoryItem

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {


    fun getStory(): LiveData<PagingData<ListStoryItem>> = storyRepository.getStory()


}