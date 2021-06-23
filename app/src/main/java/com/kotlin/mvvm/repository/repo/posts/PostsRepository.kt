package com.kotlin.mvvm.repository.repo.posts

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.mvvm.repository.api.Postservice
import com.kotlin.mvvm.repository.model.posts.Data
import com.kotlin.mvvm.repository.model.posts.PostResponse
import com.kotlin.mvvm.repository.paging.PostsPagingDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepository @Inject constructor(
    val apiServices: Postservice,
    @ApplicationContext val context: Context
) {

    fun getPostsFromServerOnly():
            Flow<PagingData<Data>> =
//        Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {
//                Log.d("post", "paging data source called in repository")
//                PostsPagingDataSource(apiServices)
//            }
//        ).flow
//        Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = { PostsPagingDataSource(apiServices) }
//        ).flow
        Pager(PagingConfig(pageSize = 20)) {
            Log.d("post", "paging data source called in repository")
            PostsPagingDataSource(apiServices)
        }.flow

}