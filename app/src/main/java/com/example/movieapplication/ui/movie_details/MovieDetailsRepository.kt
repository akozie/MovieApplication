package com.example.movieapplication.ui.movie_details

import androidx.lifecycle.LiveData
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.repository.MovieDetailsNetworkDataSource
import com.example.movieapplication.data.repository.NetworkState
import com.example.movieapplication.data.vo.TheMovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val api: TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<TheMovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(api,compositeDisposable)
        movieDetailsNetworkDataSource.getMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetails

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }

}