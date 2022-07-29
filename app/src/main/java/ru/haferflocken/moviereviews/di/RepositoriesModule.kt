package ru.haferflocken.moviereviews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.haferflocken.moviereviews.data.api.MovieReviewsApi
import ru.haferflocken.moviereviews.data.repositories.MoviesRepositoryImpl
import ru.haferflocken.moviereviews.domain.repositories.MoviesRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoriesModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(service: MovieReviewsApi): MoviesRepository =
        MoviesRepositoryImpl(service)
}