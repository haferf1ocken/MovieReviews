package ru.haferflocken.moviereviews.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import retrofit2.HttpException
import ru.haferflocken.moviereviews.data.api.MovieReviewsApi
import ru.haferflocken.moviereviews.data.mappers.toDomain
import ru.haferflocken.moviereviews.data.repositories.MoviesRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import ru.haferflocken.moviereviews.domain.entities.Movie

private const val DEFAULT_PAGE_INDEX = 0

class MoviesPagingSource(
    private val service: MovieReviewsApi,
    private val apiKey: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = service.getAllMovieReviews(
                apiKey = apiKey,
                offset = pageIndex
            )
            if (response.isSuccessful) {
                val movies = response.body()!!.movies.toDomain()
                val hasMore = response.body()!!.hasMore
                val nextKey = if (hasMore) {
                    pageIndex + (params.loadSize)
                } else {
                    null
                }

                LoadResult.Page(
                    data = movies,
                    prevKey = if (pageIndex == DEFAULT_PAGE_INDEX) null else pageIndex - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception())
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}