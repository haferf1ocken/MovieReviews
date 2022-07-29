package ru.haferflocken.moviereviews.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.haferflocken.moviereviews.domain.usecases.GetMoviesUseCase
import ru.haferflocken.moviereviews.presentation.Constants
import ru.haferflocken.moviereviews.presentation.entities.MovieUi
import ru.haferflocken.moviereviews.presentation.mappers.toUi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    val movies: Flow<PagingData<MovieUi>> = getMoviesUseCase.invoke(Constants.API_KEY)
        .map { pagingData ->
            pagingData.map { article ->
                article.toUi()
            }
        }.cachedIn(viewModelScope)
}