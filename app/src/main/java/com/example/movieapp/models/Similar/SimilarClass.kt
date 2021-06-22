package com.example.movieapp.models.Similar

import java.io.Serializable

data class SimilarClass(
    val page: Int,
    val results: List<Results>,
    val total_pages: Int,
    val total_results: Int
): Serializable