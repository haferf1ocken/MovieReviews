package ru.haferflocken.moviereviews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.haferflocken.moviereviews.data.api.MovieReviewsApi
import ru.haferflocken.moviereviews.data.api.RemoteDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMovieReviewsApi(): MovieReviewsApi {
        return RemoteDataSource.buildApi(MovieReviewsApi::class.java)
    }
}