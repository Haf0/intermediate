package com.bangkit.intermediate.api

import com.bangkit.intermediate.model.Login.LoginResponse
import com.bangkit.intermediate.model.Register.RegisterResponse
import com.bangkit.intermediate.model.stories.StoriesResponse
import com.bangkit.intermediate.model.addstories.AddStoryResponse
import com.bangkit.intermediate.model.stories.StoriesResponseWithLocation
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun registerAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginAccount(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ):Response<StoriesResponse>

    @Multipart
    @POST("stories")
    fun postStories(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ):Call<AddStoryResponse>

    @GET("stories")
    fun getAllStorieswithLocation(
        @Header("Authorization") token: String,
        @Query("location") loc : Int = 1
    ):Call<StoriesResponseWithLocation>
}