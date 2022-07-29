package ru.haferflocken.moviereviews.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import ru.haferflocken.moviereviews.R
import ru.haferflocken.moviereviews.databinding.ActivityMainBinding
import ru.haferflocken.moviereviews.extensions.observeWithLifecycle
import ru.haferflocken.moviereviews.presentation.entities.MovieUi
import ru.haferflocken.moviereviews.presentation.ui.adapters.MoviesAdapter
import ru.haferflocken.moviereviews.presentation.ui.adapters.MoviesLoadingStateAdapter
import ru.haferflocken.moviereviews.presentation.ui.custom.SpacingItemDecorator
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    @Inject lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        setUpUi()
    }

    private fun setUpUi() {
        setUpAdapter()
        setUpRecyclerView()
        observeMoviesData()
    }

    private fun setUpAdapter() {
        moviesAdapter.apply {
            addLoadStateListener { state: CombinedLoadStates ->
                val refreshState = state.refresh
                with(viewBinding) {
                    rvMovies.isVisible = refreshState != LoadState.Loading
                    progressBar.isVisible = refreshState == LoadState.Loading
                    tvEmptyMoviesList.isVisible = refreshState is LoadState.NotLoading &&
                            state.append.endOfPaginationReached &&
                            moviesAdapter.itemCount < 1
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        viewBinding.rvMovies.apply {
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadingStateAdapter(moviesAdapter),
                footer = MoviesLoadingStateAdapter(moviesAdapter)
            )
            addItemDecoration(
                SpacingItemDecorator(
                    resources.getDimensionPixelSize(R.dimen.spacing_normal_16),
                    resources.getDimensionPixelSize(R.dimen.spacing_normal_16)
                )
            )
        }
    }

    private fun observeMoviesData() {
        viewModel.movies.observeWithLifecycle(this) { movies ->
            updateUi(movies)
        }
    }

    private fun updateUi(data: PagingData<MovieUi>) = lifecycleScope.launchWhenStarted {
        moviesAdapter.submitData(data)
    }
}