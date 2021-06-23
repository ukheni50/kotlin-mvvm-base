package com.kotlin.mvvm.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kotlin.mvvm.R
import com.kotlin.mvvm.databinding.PostItemBinding
import com.kotlin.mvvm.repository.model.posts.Data
import javax.inject.Inject

class PostAdapter @Inject constructor() :
    PagingDataAdapter<Data, PostAdapter.PostHolder>(POSTCOMPARATOR) {
    private lateinit var postsArticles: PagingData<List<Data>>


    var onPostsClicked: ((Data) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding =
            PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    inner class PostHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postResponse: Data) = with(binding) {
            postText.text = postResponse.text
            Glide.with(root.context)
                .load(postResponse.image)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_banner_image)
                        .error(R.drawable.loading_banner_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(postImage)
        }
    }
    object POSTCOMPARATOR : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Data, newItem: Data) =
            oldItem == newItem
    }

}