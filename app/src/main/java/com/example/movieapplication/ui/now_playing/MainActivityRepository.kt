package com.example.movieapplication.ui.now_playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapplication.data.api.POST_PER_PAGE
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.repository.MovieDataSource
import com.example.movieapplication.data.repository.MovieDataSourceFactory
import com.example.movieapplication.data.repository.NetworkState
import com.example.movieapplication.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityRepository(private val apiService : TheMovieDBInterface) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}