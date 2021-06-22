package com.example.movieapp.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.pagination.PopularPagination
import com.example.movieapp.pagination.TopRatedPagination
import com.example.movieapp.pagination.UpcomingPagination
import com.example.movieapp.retrofit.ApiService
class MovieViewModel(val apiService: ApiService,val name:String) : ViewModel() {
    val popular = Pager(PagingConfig(499)) {
        PopularPagination(apiService)
    }.flow.cachedIn(viewModelScope)
    val topRated = Pager(PagingConfig(499)) {
        TopRatedPagination(apiService)
    }.flow.cachedIn(viewModelScope)
    val upcoming = Pager(PagingConfig(499)) {
        UpcomingPagination(apiService)
    }.flow.cachedIn(viewModelScope)
}