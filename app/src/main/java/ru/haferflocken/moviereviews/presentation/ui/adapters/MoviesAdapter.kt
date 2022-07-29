package ru.haferflocken.moviereviews.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import ru.haferflocken.moviereviews.R
import ru.haferflocken.moviereviews.databinding.ItemMoviesBinding
import ru.haferflocken.moviereviews.presentation.entities.MovieUi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesAdapter @Inject constructor()
    : PagingDataAdapter<MovieUi, MoviesAdapter.ArticlesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item as MovieUi)
    }

    class ArticlesViewHolder private constructor(
        private val viewBinding: ItemMoviesBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: MovieUi) {
            with(viewBinding) {
                val context = itemView.context

                val imageWidthPx = context.resources.getDimensionPixelSize(
                    R.dimen.movie_image_width_210
                )
                val imageHeightPx = context.resources.getDimensionPixelSize(
                    R.dimen.movie_image_height_140
                )
                val cornerRadius = context.resources.getDimension(R.dimen.card_corner_radius)

                if (item.multimedia != null) {
                    ivMovie.apply {
                        clear()
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        load(item.multimedia) {
                            memoryCachePolicy(CachePolicy.ENABLED)
                            size(imageWidthPx, imageHeightPx)
                            crossfade(true)
                            crossfade(500)
                            transformations(
                                RoundedCornersTransformation(
                                    topLeft = cornerRadius,
                                    topRight = cornerRadius
                                )
                            )
                            build()
                        }
                    }
                } else {
                    ivMovie.clear()
                    ivMovie.load(R.drawable.default_movie)
                }

                tvMovieTitle.text = item.displayTitle
                tvMovieDescription.text =
                    item.summaryShort ?: context.getString(R.string.no_description_text)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticlesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val viewBinding = ItemMoviesBinding.inflate(
                    layoutInflater, parent, false
                )
                return ArticlesViewHolder(viewBinding)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieUi>() {
        override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
            return oldItem == newItem
        }
    }
}