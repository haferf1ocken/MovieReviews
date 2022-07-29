package ru.haferflocken.moviereviews.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.haferflocken.moviereviews.domain.entities.Movie

interface MoviesRepository {
    fun getMovies(apiKey: String): Flow<PagingData<Movie>>
}