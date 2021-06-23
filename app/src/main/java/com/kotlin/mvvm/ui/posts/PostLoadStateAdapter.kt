package com.kotlin.mvvm.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mvvm.databinding.LoadStateViewBinding

class PostLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PostLoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
            return LoadStateViewHolder.create(parent, retry)
    }

    class LoadStateViewHolder(private val binding: LoadStateViewBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.loadStateRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.loadStateErrorMessage.text = loadState.error.localizedMessage
            }
            binding.loadStateProgress.isVisible = loadState is LoadState.Loading
            binding.loadStateRetry.isVisible = loadState is LoadState.Error
            binding.loadStateErrorMessage.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val binding =
                    LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return LoadStateViewHolder(binding, retry)
            }
        }
    }
}