package ru.haferflocken.moviereviews.presentation.entities

data class MovieUi(
    val displayTitle: String,
    val multimedia: String? = null,
    val summaryShort: String? = null
)