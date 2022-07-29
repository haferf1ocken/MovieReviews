package ru.haferflocken.moviereviews.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.haferflocken.moviereviews.data.api.MovieReviewsApi
import ru.haferflocken.moviereviews.domain.entities.Movie
import ru.haferflocken.moviereviews.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(
    private val service: MovieReviewsApi
) : MoviesRepository {

    override fun getMovies(apiKey: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    service = service,
                    apiKey = apiKey,
                )
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}