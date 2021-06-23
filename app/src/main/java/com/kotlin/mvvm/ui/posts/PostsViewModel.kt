package com.kotlin.mvvm.ui.posts

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.mvvm.repository.api.Postservice
import com.kotlin.mvvm.repository.model.posts.Data
import com.kotlin.mvvm.repository.model.posts.PostResponse
import com.kotlin.mvvm.repository.paging.PostsPagingDataSource
import com.kotlin.mvvm.repository.repo.posts.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(private val postsRepository: PostsRepository,val apiServices: Postservice) :
    ViewModel() {
        val myposts: Flow<PagingData<Data>> =
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PostsPagingDataSource(apiServices) }
        ).flow.cachedIn(viewModelScope)

    private fun postsFromOnlyServer(): Flow<PagingData<Data>> {
        return flow {
            viewModelScope.launch {
                Log.d("post","get post called in viewmodel")
                postsRepository.getPostsFromServerOnly().cachedIn(viewModelScope)
                Log.d("post","get post called to repository in viewmodel")
            }
        }
    }

    fun getPostsFromServer() = postsFromOnlyServer()

}