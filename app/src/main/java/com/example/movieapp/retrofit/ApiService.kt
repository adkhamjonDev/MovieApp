package com.example.movieapp.retrofit
import com.example.movieapp.models.Images.ImageModel
import com.example.movieapp.models.MainClass
import com.example.movieapp.models.Details.MovieDetails
import com.example.movieapp.models.Similar.SimilarClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {
    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
        @Query("page") page: Int
    ): MainClass
    @GET("3/search/multi")
      fun getSearchMovies(
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
        @Query("query") query: String,
    ): Call<MainClass>
    @GET("3/movie/{movie_id}")
     fun getMovieDetails(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
    ): Call<MovieDetails>
    @GET("3/movie/{movie_id}/images")
    fun getMovieImages(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
    ): Call<ImageModel>
    @GET("3/movie/{movie_id}/similar")
    fun getSimilar(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String="44f66b1676556437f4731985995f2dea",
        @Query("language") category: String="en-US",
    ): Call<SimilarClass>
}