package ru.haferflocken.moviereviews.domain.usecases

import ru.haferflocken.moviereviews.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    operator fun invoke(apiKey: String) = repository.getMovies(apiKey)
}