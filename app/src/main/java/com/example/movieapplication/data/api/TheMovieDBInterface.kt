package com.example.movieapplication.data.api


import com.example.movieapplication.data.vo.MovieResponse
import com.example.movieapplication.data.vo.TheMovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    @GET("movie/now_playing")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<TheMovieDetails>
}