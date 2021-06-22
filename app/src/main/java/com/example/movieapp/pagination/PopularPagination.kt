package com.example.movieapp.pagination
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.models.MovieClass
import com.example.movieapp.retrofit.ApiService
import java.lang.Exception
class PopularPagination(val apiService: ApiService): PagingSource<Int, MovieClass>() {
    override fun getRefreshKey(state: PagingState<Int, MovieClass>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieClass> {
        try {
            val nextPageNumber = params.key ?: 1
            val movie = apiService.getPopular(page = nextPageNumber)
            if (nextPageNumber > 1) {
                return LoadResult.Page(movie.results, nextPageNumber - 1, nextPageNumber + 1)
            } else {
                return LoadResult.Page(movie.results, null, nextPageNumber + 1)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}