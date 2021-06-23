package com.kotlin.mvvm.repository.api

import com.kotlin.mvvm.BuildConfig
import com.kotlin.mvvm.repository.model.posts.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Postservice {
    @Headers("app-id: ${BuildConfig.APP_ID}")
    @GET("post")
    suspend fun getPostsSource(
        @Query("page") page: Int, @Query("limit") limit: Int = 20
    ): PostResponse
}