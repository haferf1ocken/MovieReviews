package ru.haferflocken.moviereviews.presentation.mappers

import ru.haferflocken.moviereviews.domain.entities.Movie
import ru.haferflocken.moviereviews.presentation.entities.MovieUi

internal fun Movie.toUi(): MovieUi {
    return MovieUi(
        displayTitle = this.displayTitle,
        multimedia = this.multimedia,
        summaryShort = this.summaryShort
    )
}