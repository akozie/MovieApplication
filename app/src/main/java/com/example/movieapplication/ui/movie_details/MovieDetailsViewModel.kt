package com.example.movieapplication.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.data.repository.NetworkState
import com.example.movieapplication.data.vo.TheMovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int ): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<TheMovieDetails> =
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)


    val networkState : LiveData<NetworkState> =
        movieRepository.getMovieDetailsNetworkState()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}