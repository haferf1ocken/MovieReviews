package ru.haferflocken.moviereviews.domain.entities

data class Movie(
    val displayTitle: String,
    val multimedia: String? = null,
    val summaryShort: String? = null
)