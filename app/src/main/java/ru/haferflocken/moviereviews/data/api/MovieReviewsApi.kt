package ru.haferflocken.moviereviews.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.haferflocken.moviereviews.data.models.MoviesResponse

interface MovieReviewsApi {
    @GET("reviews/all.json")
    suspend fun getAllMovieReviews(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int
    ) : Response<MoviesResponse>
}