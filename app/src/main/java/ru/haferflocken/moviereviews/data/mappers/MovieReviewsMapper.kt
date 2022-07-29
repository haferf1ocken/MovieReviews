package ru.haferflocken.moviereviews.data.mappers

import ru.haferflocken.moviereviews.data.models.MovieResponse
import ru.haferflocken.moviereviews.domain.entities.Movie

internal fun List<MovieResponse>.toDomain(): List<Movie> {
    return this.map { movieResponse ->
        movieResponse.toDomain()
    }
}

internal fun MovieResponse.toDomain(): Movie {
    return Movie(
        displayTitle = this.displayTitle,
        multimedia = this.multimedia?.src,
        summaryShort = this.summaryShort
    )
}