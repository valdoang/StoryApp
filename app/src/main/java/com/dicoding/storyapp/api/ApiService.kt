package com.dicoding.storyapp.api

import com.dicoding.storyapp.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getListStories(
        @Header("Authorization") token: String,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): StoryResponse


    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<DetailStoryResponse>

    @GET("stories")
    fun getLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ) : Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Call<FileUploadResponse>
}