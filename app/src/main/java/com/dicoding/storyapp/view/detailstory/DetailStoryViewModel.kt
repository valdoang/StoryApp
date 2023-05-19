package com.dicoding.storyapp.view.detailstory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.api.ApiConfig
import com.dicoding.storyapp.model.DetailStoryResponse
import com.dicoding.storyapp.model.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel : ViewModel() {
    private val _detailStory = MutableLiveData<Story>()

    fun setStoryDetail(token: String, id: String) {
        val client = ApiConfig.getApiService().getDetailStory("Bearer $token",id)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _detailStory.postValue(response.body()?.story)
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getStoryDetail(): LiveData<Story> {
        return _detailStory
    }

    companion object {
        private const val TAG = "DetailStoryViewModel"
    }
}