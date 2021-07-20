package com.example.movieapplication.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.vo.TheMovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetworkDataSource(val api: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetails = MutableLiveData<TheMovieDetails>()
    val downloadedMovieDetails: LiveData<TheMovieDetails>
    get() = _downloadedMovieDetails

    fun getMovieDetails(movieID: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                    api.getMovieDetails(movieID)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        _downloadedMovieDetails.postValue(it)
                                        _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        _networkState.postValue(NetworkState.ERROR)
                                        Log.e("MovieDetailsDataSource", it.message.toString())
                                    }
                            ))

        }
        catch (e: Exception){
            Log.e("MovieDetailsDataSource",e.message.toString())

        }
    }

}