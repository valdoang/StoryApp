package com.dicoding.storyapp.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.api.ApiConfig
import com.dicoding.storyapp.model.ListStoryItem
import com.dicoding.storyapp.model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {
    val listLocation = MutableLiveData<ArrayList<ListStoryItem>>()

    fun setLocation(token: String) {
        val client = ApiConfig.getApiService().getLocation("Bearer $token", 1)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
                    listLocation.postValue(response.body()?.listStory)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getStory(): LiveData<ArrayList<ListStoryItem>> {
        return listLocation
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}