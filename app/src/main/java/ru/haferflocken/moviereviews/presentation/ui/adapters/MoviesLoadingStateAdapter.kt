package ru.haferflocken.moviereviews.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.haferflocken.moviereviews.databinding.ItemNetworkStateBinding

class MoviesLoadingStateAdapter(
    private val adapter: MoviesAdapter
) : LoadStateAdapter<MoviesLoadingStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder.from(parent) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }

        companion object {
            fun from(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val viewBinding = ItemNetworkStateBinding.inflate(
                    layoutInflater, parent, false
                )
                return NetworkStateItemViewHolder(viewBinding, retryCallback)
            }
        }
    }
}