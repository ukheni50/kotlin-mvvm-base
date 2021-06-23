package com.kotlin.mvvm.ui.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mvvm.databinding.ActivityPostsBinding
import com.kotlin.mvvm.ui.BaseActivity
import com.kotlin.mvvm.ui.news.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Posts : BaseActivity() {
    private val postViewModel: PostsViewModel by viewModels()
    private lateinit var binding: ActivityPostsBinding
    private lateinit var postadapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d("post", "activity")

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.postsList.addItemDecoration(decoration)
        postadapter = PostAdapter()
        binding.postsList.apply {
            adapter = postadapter.withLoadStateHeaderAndFooter(
                header = PostLoadStateAdapter { postadapter.retry() },
                footer = PostLoadStateAdapter { postadapter.retry() }

            )
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)

        }
        postadapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && postadapter.itemCount == 0
            showEmptyList(isListEmpty)
            // Only show the list if refresh succeeds.
            binding.postsList.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
//            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
//            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        getposts()
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
//            binding.emptyList.visibility = View.VISIBLE
            binding.postsList.visibility = View.GONE
        } else {
//            binding.emptyList.visibility = View.GONE
            binding.postsList.visibility = View.VISIBLE
        }
    }

    private fun getposts() {
        lifecycleScope.launch {
            Log.d("post", "get post called in activity")
//            postViewModel.getPostsFromServer()
//                .collectLatest { postadapter.submitData(it) }
            postViewModel.myposts.collectLatest { postadapter.submitData(it) }
        }
    }
}