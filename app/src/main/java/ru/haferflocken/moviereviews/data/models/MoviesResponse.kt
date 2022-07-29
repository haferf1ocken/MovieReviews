package ru.haferflocken.moviereviews.data.models

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val movies: List<MovieResponse>,
    val status: String
)