package com.kotlin.mvvm.repository.paging

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.mvvm.repository.api.Postservice
import com.kotlin.mvvm.repository.model.posts.Data

class PostsPagingDataSource(private val service: Postservice) :
    PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        Log.d("paging","load called")
        val pageNumber = params.key ?: 1
        return try {
            Log.d("paging","data")
            val response = service.getPostsSource(pageNumber)
            Log.d("paging", "response : $response")
            val data = response.data
            Log.d("paging", "data : $data")

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (pageNumber < response.total) response.page.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int = 1
}