package com.example.movieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.retrofit.ApiService
import java.lang.IllegalArgumentException

class ViewModelFactory(val apiService: ApiService,val name:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(apiService,name) as T
        }
        throw IllegalArgumentException("Error")
    }
}