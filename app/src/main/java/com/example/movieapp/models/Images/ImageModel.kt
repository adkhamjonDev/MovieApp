package com.example.movieapp.models.Images

data class ImageModel(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)