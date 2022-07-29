package ru.haferflocken.moviereviews.data.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("display_title")
    val displayTitle: String,
    val multimedia: MultimediaResponse? = null,
    @SerializedName("summary_short")
    val summaryShort: String? = null
)